package com.takumalee.ormcomparison.database.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DatabaseTransactionOpenHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseTransactionOpenHelper.class.getSimpleName();
    private static AtomicInteger savePointCounter = new AtomicInteger();
    private static final String SAVE_POINT_PREFIX = "ORMLITE";

    private boolean hasSavePoint = false;
    private Savepoint savePoint = null;
    private DatabaseConnection connection = null;
    private boolean autoCommitAtStart = false;

    public DatabaseTransactionOpenHelper(Context context, String databaseName, CursorFactory factory,
            int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    public boolean isInTransaction() {
        return hasSavePoint;
    }

    public void beginTransaction() {
        if (!hasSavePoint) {
            createSavePoint();
        }
    }

    private void createSavePoint() {
        savePoint = null;
        try {
            connection = connectionSource.getReadWriteConnection();
            if (connection.isAutoCommitSupported()) {
                autoCommitAtStart = connection.isAutoCommit();
                if (autoCommitAtStart) {
                    // disable auto-commit mode if supported and enabled at start
                    connection.setAutoCommit(false);
                    Log.d(TAG, "had to set auto-commit to false");
                }
            }
            savePoint = connection.setSavePoint(SAVE_POINT_PREFIX
                    + savePointCounter.incrementAndGet());
            hasSavePoint = true;
        } catch (SQLException e) {
            savePoint = null;
            hasSavePoint = false;
            restoreAutoCommit();
        }

    }

    public void commit() {
        if (hasSavePoint) {
            try {
                connection.commit(savePoint);
                Log.d(TAG, "commit  savePoint transaction");
            } catch (SQLException e) {
                DatabaseUtil.throwAndroidSQLException(TAG, e);
            } finally {
                hasSavePoint = false;
                savePoint = null;
                restoreAutoCommit();
            }
        }
    }

    public void rollBack() {
        if (hasSavePoint) {
            try {
                connection.rollback(savePoint);
                Log.d(TAG, "rolled back savePoint transaction");
            } catch (SQLException e) {
                DatabaseUtil.throwAndroidSQLException(TAG, e);
            } finally {
                hasSavePoint = false;
                savePoint = null;
                restoreAutoCommit();
            }
        }
    }

    private void restoreAutoCommit() {
        if (autoCommitAtStart) {
            try {
                connection.setAutoCommit(true);
                Log.d(TAG, "restored auto-commit to true");
            } catch (SQLException e) {
                DatabaseUtil.throwAndroidSQLException(TAG, e);
            }
        }
    }
}
