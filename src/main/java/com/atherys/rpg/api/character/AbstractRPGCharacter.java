package com.atherys.rpg.api.character;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.character.player.ProgressionTree;
import com.atherys.rpg.api.effect.Applyable;
import com.atherys.rpg.api.resource.Resource;
import com.atherys.rpg.api.skill.Castable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractRPGCharacter implements RPGCharacter {


    protected Set<Applyable> effects = new HashSet<>();
    protected Set<Attribute> attribs = new HashSet<>();
    protected Set<Castable> skills = new HashSet<>();
    protected Resource resource;

    protected Set<ProgressionTree> trees;

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

    @Override
    public Set<ProgressionTree> getTrees() {
        return trees;
    }

    @Override
    public void addTree(ProgressionTree tree) {
        if ( trees.add(tree) ) tree.getRoot().mutate(this);
    }
}
