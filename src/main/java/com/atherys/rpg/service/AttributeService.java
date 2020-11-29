package com.atherys.rpg.service;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.AttributeMapData;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class AttributeService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private RPGCharacterService characterService;

    public AttributeService() {
    }

    public Map<AttributeType, Double> getDefaultAttributes() {
        Map<AttributeType, Double> attributes = fillInAttributes(new HashMap<>(config.DEFAULT_ATTRIBUTES));
        return attributes;
    }

    public Map<AttributeType, Double> fillInAttributes(Map<AttributeType, Double> attributes) {
        for (AttributeType type : Sponge.getRegistry().getAllOf(AttributeType.class)) {
            attributes.putIfAbsent(type, 0.0);
        }

        return attributes;
    }

    public Map<AttributeType, Double> getItemStackAttributes(ItemStack stack) {
        Optional<AttributeMapData> attributeData = stack.get(AttributeMapData.class);

        if (attributeData.isPresent()) {
            return attributeData.get().getAttributes();
        } else {
            return new HashMap<>();
        }
    }

    public Map<AttributeType, Double> getOffhandAttributes(Equipable equipable) {
        return equipable.getEquipped(EquipmentTypes.OFF_HAND).map(itemStack -> {
            return config.OFFHAND_ITEMS.contains(itemStack.getType()) ? getItemStackAttributes(itemStack) : null;
        }).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getMainHandAttributes(Equipable equipable) {
        return equipable.getEquipped(EquipmentTypes.MAIN_HAND).map(itemStack -> {
            return config.MAINHAND_ITEMS.contains(itemStack.getType()) ? getItemStackAttributes(itemStack) : null;
        }).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getHelmetAttributes(ArmorEquipable equipable) {
        return equipable.getEquipped(EquipmentTypes.HEADWEAR).map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getChestplateAttributes(ArmorEquipable equipable) {
        return equipable.getEquipped(EquipmentTypes.CHESTPLATE).map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getLeggingsAttributes(ArmorEquipable equipable) {
        return equipable.getEquipped(EquipmentTypes.LEGGINGS).map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getBootsAttributes(ArmorEquipable equipable) {
        return equipable.getEquipped(EquipmentTypes.BOOTS).map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getArmorAttributes(ArmorEquipable equipable) {
        Map<AttributeType, Double> result = new HashMap<>();

        mergeAttributes(result, getHelmetAttributes(equipable));
        mergeAttributes(result, getChestplateAttributes(equipable));
        mergeAttributes(result, getLeggingsAttributes(equipable));
        mergeAttributes(result, getBootsAttributes(equipable));

        return result;
    }

    public Map<AttributeType, Double> getHeldItemAttributes(Equipable equipable) {
        Map<AttributeType, Double> result = new HashMap<>();

        mergeAttributes(result, getMainHandAttributes(equipable));
        mergeAttributes(result, getOffhandAttributes(equipable));

        return result;
    }

    /**
     * Merge the values of the two attribute type maps.<br>
     * WARNING: This will ALTER the source map
     *
     * @param source     The map to be altered
     * @param additional The additional attributes to be added
     * @return The altered source map
     */
    public Map<AttributeType, Double> mergeAttributes(Map<AttributeType, Double> source, Map<AttributeType, Double> additional) {
        additional.forEach((type, value) -> source.merge(type, value, Double::sum));
        return additional;
    }

    public Map<AttributeType, Double> getBaseAttributes(RPGCharacter<?> character) {
        return new HashMap<>(character.getBaseAttributes());
    }

    public Map<AttributeType, Double> getBuffAttributes(RPGCharacter<?> character) {
        return new HashMap<>(character.getBuffAttributes());
    }

    public Map<AttributeType, Double> getAllAttributes(Entity entity) {
        RPGCharacter<?> character = characterService.getOrCreateCharacter(entity);

        Map<AttributeType, Double> attributes = getBaseAttributes(character);
        mergeAttributes(attributes, getEquipmentAttributes(entity));
        mergeAttributes(attributes, character.getBuffAttributes());

        return attributes;
    }

    public Map<AttributeType, Double> getEquipmentAttributes(Entity entity) {
        Map<AttributeType, Double> result = new HashMap<>();

        if (entity instanceof Equipable) {
            mergeAttributes(result, getHeldItemAttributes((Equipable) entity));
        }

        if (entity instanceof ArmorEquipable) {
            mergeAttributes(result, getArmorAttributes((ArmorEquipable) entity));
        }

        return result;
    }
}
