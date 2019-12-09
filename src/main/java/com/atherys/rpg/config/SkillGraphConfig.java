package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.*;

@ConfigSerializable
public class SkillGraphConfig {

    @Setting("root-skill")
    public SkillNodeConfig ROOT = new SkillNodeConfig();

    @Setting("skill-nodes")
    public Map<String, SkillNodeConfig> NODES = new HashMap<>();

    @Setting("skill-links")
    public List<SkillNodeLinkConfig> LINKS = new ArrayList<>();
}
