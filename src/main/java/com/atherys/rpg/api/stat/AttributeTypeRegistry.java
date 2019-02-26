package com.atherys.rpg.api.stat;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class AttributeTypeRegistry implements CatalogRegistryModule<AttributeType> {

    private Map<String,AttributeType> attributeTypeMap = new HashMap<>();

    public AttributeTypeRegistry() {
        attributeTypeMap.put(AttributeTypes.STRENGTH.getId(), AttributeTypes.STRENGTH);
        attributeTypeMap.put(AttributeTypes.CONSTITUTION.getId(), AttributeTypes.CONSTITUTION);
        attributeTypeMap.put(AttributeTypes.DEFENSE.getId(), AttributeTypes.DEFENSE);
        attributeTypeMap.put(AttributeTypes.AGILITY.getId(), AttributeTypes.AGILITY);
        attributeTypeMap.put(AttributeTypes.INTELLIGENCE.getId(), AttributeTypes.INTELLIGENCE);
        attributeTypeMap.put(AttributeTypes.CHARISMA.getId(), AttributeTypes.CHARISMA);
        attributeTypeMap.put(AttributeTypes.WISDOM.getId(), AttributeTypes.WISDOM);
        attributeTypeMap.put(AttributeTypes.WILLPOWER.getId(), AttributeTypes.WILLPOWER);
        attributeTypeMap.put(AttributeTypes.PERCEPTION.getId(), AttributeTypes.PERCEPTION);
        attributeTypeMap.put(AttributeTypes.LUCK.getId(), AttributeTypes.LUCK);
    }

    @Override
    public Optional<AttributeType> getById(String id) {
        return Optional.ofNullable(attributeTypeMap.get(id));
    }

    @Override
    public Collection<AttributeType> getAll() {
        return attributeTypeMap.values();
    }
}
