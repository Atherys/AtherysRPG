package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.util.SimpleIdentifiable;
import org.spongepowered.api.text.TextRepresentable;

/**
 * A Castable is something which a {@link CastableCarrier} may invoke.
 */
public interface Castable extends SimpleIdentifiable, TextRepresentable {

    CastableProperties getProperties();

    CastResult cast(CastableCarrier user, String... args);

    boolean equals(Object other);

    int hashCode();

}
