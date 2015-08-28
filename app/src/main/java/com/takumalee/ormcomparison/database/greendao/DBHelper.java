package com.takumalee.ormcomparison.database.greendao;

import android.content.Context;

import com.takumalee.ormcomparison.database.greendao.dao.CharacterDao;
import com.takumalee.ormcomparison.database.greendao.dao.DaoMaster;
import com.takumalee.ormcomparison.database.greendao.dao.DaoSession;

import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by TakumaLee on 15/8/27.
 */
public class DBHelper {
    private static DBHelper INSTANCE = null;

    /**
     * not thread-safe
     */
    public static DBHelper getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = new DBHelper(context);
        return INSTANCE;
    }

    private static final String DB_NAME = "greendao.db";
    private DaoSession daoSession;
    private AsyncSession asyncSession;

    private DBHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public CharacterDao getCharacterDao() {
        return daoSession.getCharacterDao();
    }

    public AsyncSession getAsyncSession(){
        return asyncSession;
    }
}
