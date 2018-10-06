package com.atherys.rpg.model.mutators;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.google.gson.annotations.Expose;

public class MaxHealthMutator implements Mutator {

    @Expose private double maxHealth;

    public MaxHealthMutator(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void mutate(RPGCharacter character) {
        character.setMaxHealth(maxHealth);
    }
}
