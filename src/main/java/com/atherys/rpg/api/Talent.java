package com.atherys.rpg.api;

import com.atherys.rpg.api.character.RPGCharacter;

/**
 * Things that a Talent should:
 * - Alter the player character's skill list ( the skills they have available to them to use )<br>
 * - Alter the player character's skill properties<br>
 * - Alter the player character's max health<br>
 * - Alter the player character's health regen<br>
 * - Alter the player character's max resources<br>
 * - Alter the player character's resource regen<br>
 * - Alter the player character's damage applied from specific damage types<br>
 * - Alter the player character's defense against specific damage types<br>
 * - Be revertable ( any changes made by a talent can be unmade )
 */
public interface Talent {

    void apply(RPGCharacter character);

    void revert(RPGCharacter character);

}
