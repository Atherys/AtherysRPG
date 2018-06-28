package com.atherys.rpg.attribute;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.attribute.AttributeCarrier;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.util.Identifiable;

public abstract class AbstractAttribute<T extends Event> implements Attribute<T> {

    private String id;
    private String name;

    private Class<T> clazz;

    public AbstractAttribute(String id, String name, Class<T> clazz) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
    }

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
                apply((T) event, carrier);
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<T> getEventClass() {
        return clazz;
    }

    protected abstract void apply(T event, AttributeCarrier carrier);
}
