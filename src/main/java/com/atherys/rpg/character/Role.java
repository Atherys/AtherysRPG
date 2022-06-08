package com.atherys.rpg.character;

import com.atherys.rpg.api.skill.RPGSkill;
import org.hibernate.mapping.Collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Role {
    public static final Role citizen = new Role("Citizen", Collections.emptySet(), "You are nothing.");
    private String name;
    private Set<RPGSkill> skills;
    private String description;

    public Role(String name, Set<RPGSkill> skills, String description) {
        this.name = name;
        this.skills = skills;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Set<RPGSkill> getSkills() {
        return skills;
    }

    public String getDescription() {
        return description;
    }
}
