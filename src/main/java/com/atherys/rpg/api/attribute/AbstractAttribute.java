package com.atherys.rpg.api.attribute;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.util.Identifiable;

public abstract class AbstractAttribute<T extends Event> implements Attribute<Event> {

    @Override
    public <C extends AttributeCarrier> void attachTo(C carrier) {
        carrier.addAttribute(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void notify(Event event, AttributeCarrier carrier) {
        if ( getEventClass().isAssignableFrom(event.getClass()) ) {
            Object root = event.getCause().root();
            if ( root instanceof Identifiable && ((Identifiable) root).getUniqueId().equals(carrier.getUniqueId()) ) {
                apply((T) event);
            }
        }
    }

    protected abstract void apply(T event);
}
