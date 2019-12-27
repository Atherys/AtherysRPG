package com.atherys.rpg.api.stat;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class AttributeTypeRegistry implements CatalogRegistryModule<AttributeType> {

    private Map<String, AttributeType> attributeTypeMap = new HashMap<>();

    public AttributeTypeRegistry() {
        attributeTypeMap.put(AttributeTypes.STRENGTH.getId(), AttributeTypes.STRENGTH);
        attributeTypeMap.put(AttributeTypes.CONSTITUTION.getId(), AttributeTypes.CONSTITUTION);
        attributeTypeMap.put(AttributeTypes.DEXTERITY.getId(), AttributeTypes.DEXTERITY);
        attributeTypeMap.put(AttributeTypes.INTELLIGENCE.getId(), AttributeTypes.INTELLIGENCE);
        attributeTypeMap.put(AttributeTypes.WISDOM.getId(), AttributeTypes.WISDOM);
        attributeTypeMap.put(AttributeTypes.MAGICAL_RESISTANCE.getId(), AttributeTypes.MAGICAL_RESISTANCE);
        attributeTypeMap.put(AttributeTypes.PHYSICAL_RESISTANCE.getId(), AttributeTypes.PHYSICAL_RESISTANCE);
        attributeTypeMap.put(AttributeTypes.BASE_ARMOR.getId(), AttributeTypes.BASE_ARMOR);
        attributeTypeMap.put(AttributeTypes.BASE_DAMAGE.getId(), AttributeTypes.BASE_DAMAGE);
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
