package com.atherys.rpg.api.attribute;

import com.atherys.rpg.api.Identifyable;
import com.atherys.rpg.api.effect.Applyable;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.TextRepresentable;

/**
 * An Attribute can be stored on to any DataHolder and will modify the game experience for the player
 * in a certain way based on incoming events relevant to that specific player. This differs from an {@link Applyable}
 * as the effects here are meant to be more permanent and persisted across server restarts.
 *
 * @param <T> The event this Attribute will respond to
 */
public interface Attribute<T extends Event> extends Identifyable, TextRepresentable {

    /**
     * Attaches this attribute to an {@link AttributeCarrier}
     *
     * @param carrier The character this attribute will be attached to.
     */
    <C extends AttributeCarrier> void attachTo(C carrier);

    /**
     * Notify this Attribute of an incoming event
     *
     * @param event The event this attribute is to be notified of
     */
    void notify(T event);

    /**
     * Return the event class this attribute listens for. Is used in filtering out irrelevant events.
     *
     * @return The class this Attribute listens for.
     */
    Class<T> getEventClass();

    /**
     * Get the Custom Data object responsible for persisting this Attribute on the player
     *
     * @return an AbstractData instance
     */
    AbstractData asData();
}
