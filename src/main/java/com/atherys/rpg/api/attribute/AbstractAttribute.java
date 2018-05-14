package com.atherys.rpg.api.attribute;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.event.Event;

public abstract class AbstractAttribute<T extends Event,M extends DataManipulator<M, I>,I extends ImmutableDataManipulator<I, M>> extends AbstractData<M,I> implements Attribute<Event> {

    @Override
    public <C extends AttributeCarrier> void attachTo(C carrier) {
        carrier.addAttribute(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void notify(Event event) {
        if ( getEventClass().isAssignableFrom(event.getClass()) ) apply((T) event);
    }

    protected abstract void apply(T event);
}
