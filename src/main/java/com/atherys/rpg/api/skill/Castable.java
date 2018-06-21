package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.util.SimpleIdentifiable;
import org.spongepowered.api.text.Text;

/**
 * A Castable is something which a {@link CastableCarrier} may invoke.
 */
public interface Castable extends SimpleIdentifiable {

    CastResult cast(CastableCarrier user, String... args);

    CastableProperties getDefaultProperties();

    default CastableProperties getProperties(CastableCarrier character) {
        return character.getProperties(this).orElse(getDefaultProperties());
    }

    Text asText(RPGCharacter character);

    boolean equals(Object other);

    int hashCode();

}
