package com.atherys.rpg.attribute;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.attribute.AttributeCarrier;

import java.util.HashSet;
import java.util.Set;

public class AttributeService {

    private static AttributeService instance = new AttributeService();

    private Set<Attribute<?>> defaultAttributes = new HashSet<>();

    private AttributeService() {
    }

    public boolean addDefault(Attribute<?> attribute) {
        return defaultAttributes.add(attribute);
    }

    public boolean removeDefault(Attribute<?> attribute) {
        return defaultAttributes.remove(attribute);
    }

    public void populateDefaults(AttributeCarrier carrier) {
        defaultAttributes.forEach(carrier::addAttribute);
    }

    public static AttributeService getInstance() {
        return instance;
    }

}
