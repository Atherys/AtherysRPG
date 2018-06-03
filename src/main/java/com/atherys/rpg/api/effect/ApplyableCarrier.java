package com.atherys.rpg.api.effect;

import com.atherys.rpg.api.LivingRepresentable;
import org.spongepowered.api.entity.living.Living;

import java.util.Collection;
import java.util.Optional;

/**
 * Represents a {@link Living} object which can carry and be effected by {@link Applyable}s.
 */
public interface ApplyableCarrier extends LivingRepresentable {

    /**
     * Get the list of effects currently being applied to this carrier
     *
     * @return The list of effects
     */
    Collection<Applyable> getEffects();

    /**
     * Get an Applyable carried by this object based on its String id.
     *
     * @param id The String id to look for
     * @return The Applyable instance. An empty optional if none is found.
     */
    Optional<? extends Applyable> getAppliedEffectById(String id);

    /**
     * Apply a new effect to the carrier
     *
     * @param effect    the effect to be applied
     * @param timestamp The timestamp of when it is being applied
     * @return Whether or not it was applied successfully
     */
    <T extends Applyable> boolean apply(T effect, long timestamp);

    /**
     * Remove an effect from the carrier
     *
     * @param effect    The effect to be removed
     * @param timestamp A timestamp of when it is being removed
     * @return Whether or not it was removed successfully
     */
    <T extends Applyable> boolean remove(T effect, long timestamp);

}
