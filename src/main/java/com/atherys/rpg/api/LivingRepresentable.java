package com.atherys.rpg.api;

import org.spongepowered.api.entity.living.Living;

import java.util.Optional;

/**
 * An object which can be represented as a Sponge {@link Living} entity.
 */
public interface LivingRepresentable {

    /**
     * Get the underlying Living entity from this carrier
     *
     * @return The Living instance. Empty optional if it is not present.
     */
    Optional<? extends Living> asLiving();

}
