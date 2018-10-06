package com.atherys.rpg.api;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.attribute.AttributeCarrier;

public interface AttributeService {
    boolean addDefault(Attribute<?> attribute);

    boolean removeDefault(Attribute<?> attribute);

    void populateDefaults(AttributeCarrier carrier);
}
