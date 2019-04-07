package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SkillNodeConfig {

    @Setting("skill-id")
    private String SKILL_ID = "skill-id";

    @Setting("cooldown")
    private String COOLDOWN_EXPRESSION = "5000";

    @Setting("cost")
    private String COST_EXPRESSION = "5.0";

}
