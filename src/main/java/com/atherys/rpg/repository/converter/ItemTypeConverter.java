package com.atherys.rpg.repository.converter;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import javax.persistence.AttributeConverter;

public class ItemTypeConverter implements AttributeConverter<ItemType, String> {
    @Override
    public String convertToDatabaseColumn(ItemType attribute) {
        return attribute.getId();
    }

    @Override
    public ItemType convertToEntityAttribute(String dbData) {
        return Sponge.getRegistry().getType(ItemType.class, dbData).orElse(ItemTypes.AIR);
    }
}
