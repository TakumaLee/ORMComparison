package com.takumalee.ormcomparison.database.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by TakumaLee on 15/8/29.
 */
public class RealmCharacter extends RealmObject {

    @PrimaryKey
    private int id;
    private String careers;
    private String attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
