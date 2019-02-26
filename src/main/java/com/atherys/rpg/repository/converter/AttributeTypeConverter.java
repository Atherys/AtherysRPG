package com.atherys.rpg.repository.converter;

import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.Sponge;

import javax.persistence.AttributeConverter;

public class AttributeTypeConverter implements AttributeConverter<AttributeType, String> {
    @Override
    public String convertToDatabaseColumn(AttributeType attribute) {
        return attribute.getId();
    }

    @Override
    public AttributeType convertToEntityAttribute(String dbData) {
        return Sponge.getRegistry().getType(AttributeType.class, dbData).orElse(null);
    }
}
