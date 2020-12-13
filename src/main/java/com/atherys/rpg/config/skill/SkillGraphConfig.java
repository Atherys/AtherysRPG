package com.atherys.rpg.config.skill;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.*;

@Singleton
public class SkillGraphConfig extends PluginConfig {

    @Setting("root-skill")
    public SkillNodeConfig ROOT = new SkillNodeConfig();

    @Setting("skill-nodes")
    public Map<String, SkillNodeConfig> NODES = new HashMap<>();

    @Setting("skill-links")
    public List<SkillNodeLinkConfig> LINKS = new ArrayList<>();

    @Setting("unique-skills")
    public Set<String> UNIQUE_SKILLS = new HashSet<>();

    protected SkillGraphConfig() throws IOException {
        super("config/atherysrpg", "skill-graph.json");
    }
}
