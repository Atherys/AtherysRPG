package com.atherys.rpg.api.skill;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.text.TextRepresentable;

/**
 * A Castable is something which a {@link CastableCarrier} may invoke.
 */
public interface Castable extends CatalogType, TextRepresentable {

    CastableProperties getProperties();

    CastResult cast(CastableCarrier user, String... args);

}
