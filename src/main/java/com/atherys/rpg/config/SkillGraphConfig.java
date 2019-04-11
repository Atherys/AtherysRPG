package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ConfigSerializable
public class SkillGraphConfig {

    @Setting("skill-nodes")
    public Map<String, SkillNodeConfig> NODES = new HashMap<>();

    @Setting("skill-links")
    public Set<SkillNodeLinkConfig> LINKS = new HashSet<>();

}
