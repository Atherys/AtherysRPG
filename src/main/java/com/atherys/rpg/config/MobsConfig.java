package com.atherys.rpg.config;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MobsConfig extends PluginConfig {
    protected MobsConfig() throws IOException {
        super("config/atherysrpg", "monsters.conf");
    }

    @Setting("mobs")
    public Map<String, MobConfig> MOBS = new HashMap<>();

    {
        MOBS.put("cow", new MobConfig());
    }
}
