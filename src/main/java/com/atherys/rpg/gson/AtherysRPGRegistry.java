package com.atherys.rpg.gson;

import com.atherys.core.gson.TypeAdapterFactoryRegistry;

public class AtherysRPGRegistry extends TypeAdapterFactoryRegistry {

    private static AtherysRPGRegistry instance = new AtherysRPGRegistry();

    public static AtherysRPGRegistry getInstance() {
        return instance;
    }

}
