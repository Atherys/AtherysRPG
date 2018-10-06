package com.atherys.rpg.service;

import com.atherys.rpg.api.AttributeService;
import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.attribute.AttributeCarrier;

import java.util.HashSet;
import java.util.Set;

public class RPGAttributeService implements AttributeService {

    private static RPGAttributeService instance = new RPGAttributeService();

    private Set<Attribute<?>> defaultAttributes = new HashSet<>();

    private RPGAttributeService() {
    }

    @Override
    public boolean addDefault(Attribute<?> attribute) {
        return defaultAttributes.add(attribute);
    }

    @Override
    public boolean removeDefault(Attribute<?> attribute) {
        return defaultAttributes.remove(attribute);
    }

    @Override
    public void populateDefaults(AttributeCarrier carrier) {
        defaultAttributes.forEach(carrier::addAttribute);
    }

    public static RPGAttributeService getInstance() {
        return instance;
    }

}
