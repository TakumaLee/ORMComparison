package com.takumalee.ormcomparison.database.ormlite.dao;

import android.content.Context;

import com.takumalee.ormcomparison.database.ormlite.DatabaseHelper;

import java.sql.SQLException;

public class DAOFactory {

    private static final String TAG = DAOFactory.class.getSimpleName();
    private static DAOFactory instance = null;

    protected Context context;
    protected DatabaseHelper dbHelper;

    /* Constructors */
    private DAOFactory(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public static synchronized DAOFactory initSingleton(Context context) {
        if (instance == null && context != null) {
            Context appContext = context.getApplicationContext();
            instance = new DAOFactory(appContext);
        }
        return instance;
    }

    public static synchronized DAOFactory getInstance() {
        return instance;
    }

    /* Transaction-oriented methods */
    public synchronized void beginTransaction() {
        dbHelper.beginTransaction();
    }

    public synchronized void commitTransaction() {
        dbHelper.commit();
    }

    public synchronized void rollbackTransaction() {
        dbHelper.rollBack();
    }

    public synchronized void EraseAllData() {
        dbHelper.eraseAllData(dbHelper.getReadableDatabase(), dbHelper.getConnectionSource());
    }

    public synchronized DatabaseHelper getDbHelper() {
        return dbHelper;
    }

}
