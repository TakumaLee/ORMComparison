package com.takumalee.ormcomparison.database.ormlite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.takumalee.ormcomparison.database.ormlite.model.Character;

import java.sql.SQLException;

/**
 * Created by TakumaLee on 15/8/27.
 */
public class CharacterDao extends BaseDaoImpl<Character, Integer> implements Dao<Character, Integer> {
    public CharacterDao(Class<Character> dataClass) throws SQLException {
        super(dataClass);
    }

    public CharacterDao(ConnectionSource connectionSource, Class<Character> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CharacterDao(ConnectionSource connectionSource, DatabaseTableConfig<Character> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
