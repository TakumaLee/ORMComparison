package com.takumalee.ormcomparison;

import android.app.Application;
import android.util.Log;

import com.takumalee.ormcomparison.context.ApplicationContextSingleton;
import com.takumalee.ormcomparison.database.ormlite.dao.DAOFactory;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TakumaLee on 15/1/25.
 */
public class ORMApplication extends Application {
    private static final String TAG = ORMApplication.class.getSimpleName();

    public RealmConfiguration realmConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Service is Not Running.");
        ApplicationContextSingleton.initialize(getApplicationContext());
        DAOFactory.initSingleton(getApplicationContext());

        realmConfig = new RealmConfiguration.Builder(getApplicationContext())
                .name("Realm.sql")
                .build();
        Realm.setDefaultConfiguration(realmConfig);

    }

}
