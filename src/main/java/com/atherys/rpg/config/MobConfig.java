package com.atherys.rpg.config;

import com.atherys.rpg.api.stat.AttributeType;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class MobConfig {
    @Setting("default-attributes")
    public Map<AttributeType, Double> DEFAULT_ATTRIBUTES = new HashMap<>();

    @Setting("damage-expression")
    public String DAMAGE_EXPRESSION = "5.0";

    @Setting("experience")
    public Double EXPERIENCE = 10.0;
}
