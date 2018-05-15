package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.Identifyable;
import org.spongepowered.api.text.TextRepresentable;

/**
 * A Castable is something which a {@link CastableCarrier} may invoke.
 */
public interface Castable extends Identifyable, TextRepresentable {

    CastableProperties getProperties();

    CastResult cast(CastableCarrier user, String... args);

}
