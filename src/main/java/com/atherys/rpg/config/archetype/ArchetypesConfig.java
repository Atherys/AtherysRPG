package com.atherys.rpg.config.archetype;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class ArchetypesConfig extends PluginConfig {
    public ArchetypesConfig() throws IOException {
        super("config/atherysrpg/", "archetypes.conf");
    }

    @Setting("archetypes")
    public Set<ArchetypeConfig> ARCHETYPES = new HashSet<>();

    @Setting("default-archetype")
    public String DEFAULT = "None";
}
