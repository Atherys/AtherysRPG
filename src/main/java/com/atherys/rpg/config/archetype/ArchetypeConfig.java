package com.atherys.rpg.config.archetype;

import ninja.leaping.configurate.objectmapping.Setting;

import java.util.HashSet;
import java.util.Set;

public class ArchetypeConfig {
    @Setting("id")
    public String ID;

    @Setting("display-name")
    public String NAME;

    @Setting("skills")
    public Set<String> SKILLS = new HashSet<>();
}
