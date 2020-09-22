package com.atherys.rpg.config;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class LootablesConfig extends PluginConfig {

    @Setting("lootables")
    public List<LootableConfig> LOOTABLES = new ArrayList<>();
    {
        LOOTABLES.add(new LootableConfig());
    }

    protected LootablesConfig() throws IOException {
        super("config/atherysrpg", "lootables.conf");
    }
}
