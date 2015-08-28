package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class OrmDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "com.takumalee.ormcomparison.database.greendao.dao");
        createTable(schema);
        generateDaoFiles(schema);
    }

    private static void createTable(Schema schema){
        //一個Entity 對應一個 DB table
        Entity point = schema.addEntity("Character");

        //add table column
        point.addIdProperty();
        point.addStringProperty("careers").notNull();
        point.addStringProperty("attributes").notNull();
    }

    private static void generateDaoFiles(Schema schema){
        try {
            DaoGenerator generator = new DaoGenerator();
            //建立到指定目錄
            generator.generateAll(schema, "../ORMComparison/app/src/main/java/");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
