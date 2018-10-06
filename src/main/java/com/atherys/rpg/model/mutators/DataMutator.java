package com.atherys.rpg.model.mutators;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;

/**
 * A Mutator which will modify some data on the Living entity represented by the RPGCharacter
 */
public class DataMutator<E> implements Mutator {

    @Expose private Key<? extends BaseValue<E>> key;
    @Expose private E value;

    public DataMutator ( Key<? extends BaseValue<E>> key, E value ) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void mutate(RPGCharacter character) {
        character.asLiving().ifPresent(living -> living.offer(key, value));
    }
}
