package com.takumalee.ormcomparison.database.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.takumalee.ormcomparison.database.ormlite.dao.CharacterDao;

/**
 * Created by TakumaLee on 15/8/27.
 */
@DatabaseTable(tableName = "character", daoClass = CharacterDao.class)
public class Character {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String careers;
    @DatabaseField(canBeNull = false)
    private String attributes;

    public Character() {
        careers = "";
        attributes = "";
    }

    public String getCareers() {
        return careers;
    }

    public void setCareers(String careers) {
        this.careers = careers;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
