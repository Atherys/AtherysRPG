package com.atherys.rpg.db;

import com.atherys.core.database.mongo.AbstractMongoDatabase;
import com.atherys.rpg.AtherysRPG;

public class RPGDatabase extends AbstractMongoDatabase {

    private static RPGDatabase instance = new RPGDatabase();

    private RPGDatabase() {
        super(AtherysRPG.getConfig().DATABASE_CONFIG);
    }

    public static RPGDatabase getInstance() {
        return instance;
    }
}
