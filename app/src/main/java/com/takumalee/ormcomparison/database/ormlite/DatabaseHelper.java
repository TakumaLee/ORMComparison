package com.takumalee.ormcomparison.database.ormlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.takumalee.ormcomparison.context.ApplicationContextSingleton;
import com.takumalee.ormcomparison.database.ormlite.model.*;
import com.takumalee.ormcomparison.database.ormlite.model.Character;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;


/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 */
public class DatabaseHelper extends DatabaseTransactionOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "ormlite.sql";
    private static final String DATABASE_PATH = ApplicationContextSingleton.getApplicationContext().getFilesDir().getAbsolutePath() + "/";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int DB_Ver;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Character.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
    }

    public void eraseAllData(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.clearTable(connectionSource, Character.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eraseAllData() {
        eraseAllData(getReadableDatabase(), getConnectionSource());
    }

    private void copyDatabase() {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in;
            in = assetManager.open(DATABASE_NAME);
            OutputStream out = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;

        String myPath = DATABASE_PATH + DATABASE_NAME;
        File dbfile = new File(myPath);
        checkdb = dbfile.exists();

        Log.i(DatabaseHelper.class.getName(), "DB Exist : " + checkdb);

        return checkdb;
    }

    private void getDBVersionFromSharedPreferences() {
        try {
            sharedPreferences = context.getSharedPreferences("DATABASE", 0);
            DB_Ver = sharedPreferences.getInt("DATABASE_VERSION", 0);
        } catch (Exception e) {
        }
    }

    private void saveSharedPreferences() {
        try {
            editor = sharedPreferences.edit();
            editor.putInt("DATABASE_VERSION", DATABASE_VERSION);
            editor.commit();
        } catch (Exception e) {
        }
    }

    private void saveUpdateSharedPreferences() {
        editor = sharedPreferences.edit();
        editor.putBoolean("DATABASE_VERSION2", true);
        editor.commit();
    }
}

