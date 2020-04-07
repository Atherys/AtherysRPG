package com.atherys.rpg.config;

import com.atherys.rpg.api.stat.AttributeType;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigSerializable
public class MobConfig {
    @Setting("default-attributes")
    public Map<AttributeType, Double> DEFAULT_ATTRIBUTES = new HashMap<>();

    @Setting("damage-expression")
    public String DAMAGE_EXPRESSION = "5.0";

    @Setting("health-limit-expression")
    public String HEALTH_LIMIT_EXPRESSION = "100.0 * SOURCE_INT";

    @Setting("item-drop-limit")
    public int ITEM_DROP_LIMIT = 2;

    @Setting("loot")
    public List<LootConfig> LOOT = new ArrayList<>();
}
