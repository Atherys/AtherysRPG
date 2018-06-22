package com.atherys.rpg.mutators;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.google.gson.annotations.Expose;

/**
 * A Mutator which will grant the given attribute to the character
 */
public class AttributeMutator implements Mutator {

    @Expose private Attribute<?> attribute;

    public AttributeMutator(Attribute<?> attribute) {
        this.attribute = attribute;
    }


    @Override
    public void mutate(RPGCharacter character) {
        character.addAttribute(attribute.copy());
    }
}
