package com.atherys.rpg.api.character;

/**
 * A Mutator is defined as being a single unit of modification applied to an RPGCharacter.
 * It can change any property inherent to the character, or the tree it represents.
 */
public interface Mutator {

    void mutate(RPGCharacter character);

}
