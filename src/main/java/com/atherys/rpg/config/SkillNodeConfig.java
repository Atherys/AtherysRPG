package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class SkillNodeConfig {

    @Setting("skill-id")
    public String SKILL_ID = "skill-id";

    @Setting("cooldown")
    public String COOLDOWN_EXPRESSION = "5000";

    @Setting("cost")
    public String COST_EXPRESSION = "5.0";

    @Setting("properties")
    public Map<String, String> PROPERTIES = new HashMap<>();

}
