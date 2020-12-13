package com.atherys.rpg.config.skill;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class SkillNodeConfig {

    @Setting("skill-id")
    public String SKILL_ID = "rpg-simple-damage";

    @Setting("cooldown")
    public String COOLDOWN_EXPRESSION;

    @Setting("cost")
    public String COST_EXPRESSION;

    @Setting("properties")
    public Map<String, String> PROPERTIES = new HashMap<>();

}
