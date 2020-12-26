package com.atherys.rpg.api.stat;

import com.atherys.rpg.config.stat.AttributesConfig;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.io.IOException;
import java.util.*;

public final class AttributeTypeRegistry implements CatalogRegistryModule<AttributeType> {

    private Map<String, AttributeType> attributeTypeMap = new LinkedHashMap<>();

    public AttributeTypeRegistry() {
        try {
            AttributesConfig config = new AttributesConfig();
            config.init();

            config.ATTRIBUTE_TYPES.stream().map(conf -> new AttributeType(
                    conf.getId(),
                    conf.getShortName(),
                    conf.getName(),
                    conf.getDescription(),
                    conf.isUpgradable(),
                    conf.isHidden(),
                    conf.getColor(),
                    conf.getDefaultValue(),
                    conf.isResetOnLogin()
            )).forEach(type -> attributeTypeMap.put(type.getId(), type));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
