package com.takumalee.ormcomparison.context;

import android.content.Context;

public class ApplicationContextSingleton {
    private static ApplicationContextSingleton INSTANCE;

    private Context appContext;

    private ApplicationContextSingleton(Context context){
        this.appContext = context;
    }
    
    public static synchronized ApplicationContextSingleton initialize(Context context){
        if (INSTANCE == null) {
            INSTANCE = new ApplicationContextSingleton(context.getApplicationContext());
        }
        return INSTANCE;
    }
    
    public static Context getApplicationContext() {
        return INSTANCE.appContext;
    }

    public static ApplicationContextSingleton getInstance() {
        return INSTANCE;
    }

}
