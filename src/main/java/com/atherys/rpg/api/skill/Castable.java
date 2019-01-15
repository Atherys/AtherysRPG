package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.exception.CastException;
import com.atherys.rpg.api.util.SimpleIdentifiable;
import org.spongepowered.api.text.Text;

/**
 * A Castable is something which a {@link CastableCarrier} may invoke.
 */
public interface Castable extends SimpleIdentifiable {

    /**
     * Triggers the use of this Castable by the provided CastableCarrier, at the specified timestamp with the provided arguments
     *
     * @param user The user of the skill
     * @param timestamp The timestamp of when the skill was used
     * @param args The arguments
     * @return A {@link CastResult}
     */
    CastResult cast(CastableCarrier user, long timestamp, String... args) throws CastException;

    /**
     * Retrieves the default properties of this skill
     *
     * @return The default properties
     */
    CastableProperties getDefaultProperties();

    /**
     * Retrieves the player-specific properties for this skill from the specified CastableCarrier
     *
     * @param character the character whose properties are to be retrieved
     * @return The properties
     */
    default CastableProperties getProperties(CastableCarrier character) {
        return character.getProperties(this).orElse(getDefaultProperties());
    }

    /**
     * Returns the Castable in a viewer-friendly readable state. Is dependent on character properties for this skill.
     *
     * @param character The character whose properties are to be used when filling out the text
     * @return The Text object
     */
    Text asText(RPGCharacter character);

    boolean equals(Object other);

    int hashCode();

}
