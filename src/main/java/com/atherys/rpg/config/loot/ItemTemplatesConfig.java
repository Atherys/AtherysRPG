package com.atherys.rpg.config.loot;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ItemTemplatesConfig extends PluginConfig {

    @Setting("item-templates")
    public Map<String, List<String>> ITEM_TEMPLATES = new HashMap<>();

    @Setting("rarities")
    public Map<String, String> RARITIES = new HashMap<>();

    @Setting("categories")
    public Map<String, String> CATEGORIES = new HashMap<>();

    public ItemTemplatesConfig() throws IOException {
        super("config/atherysrpg", "item-templates.conf");
    }
}
