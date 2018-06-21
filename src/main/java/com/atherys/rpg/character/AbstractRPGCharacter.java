package com.atherys.rpg.api.character;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.effect.Applyable;
import com.atherys.rpg.api.resource.Resource;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableProperties;

import java.util.*;

public abstract class AbstractRPGCharacter implements RPGCharacter {


    protected Set<Applyable> effects = new HashSet<>();
    protected Set<Attribute> attribs = new HashSet<>();

    protected Map<Castable,CastableProperties> skills = new HashMap<>();

    protected Resource resource;

    protected AbstractRPGCharacter() {
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attribs;
    }

    @Override
    public Set<Applyable> getEffects() {
        return effects;
    }

    @Override
    public Set<Castable> getCastables() {
        return skills.keySet();
    }

    @Override
    public Map<Castable,CastableProperties> getCastableProperties() {
        return skills;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
