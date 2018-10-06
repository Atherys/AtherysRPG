package com.atherys.rpg.model.mutators;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableProperties;
import com.google.gson.annotations.Expose;

/**
 * A Mutator which will add the given Castable to the RPGCharacter, allowing them to use it
 */
public class SkillMutator implements Mutator {

    @Expose private Castable castable;

    @Expose private CastableProperties properties;

    public SkillMutator(Castable castable, CastableProperties properties) {
        this.castable = castable;
        this.properties = properties;
    }

    @Override
    public void mutate(RPGCharacter character) {
        character.putCastable(castable, properties);
    }
}
