package com.atherys.rpg.service;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.config.stat.AttributesConfig;
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

    private Map<AttributeType, Double> defaultAttributes;

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private AttributesConfig attributesConfig;

    @Inject
    private RPGCharacterService characterService;

    public AttributeService() {
    }

    /**
     * Returns a map containing the default values configured for each attribute
     * @return Map
     */
    public Map<AttributeType, Double> getDefaultAttributes() {
        if (defaultAttributes == null) {
            defaultAttributes = new HashMap<>();
            Sponge.getRegistry().getAllOf(AttributeType.class).forEach( type -> {
                defaultAttributes.put(type, Math.max(0, type.getDefaultValue()));
            });
        }
        return defaultAttributes;
    }

    /**
     * Set any missing attributes to 0
     * @param attributes Attribute hashmap to modify
     * @return modified attributes with missing attributes set to 0
     */
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
    private Map<AttributeType, Double> mergeAttributes(Map<AttributeType, Double> source, Map<AttributeType, Double> additional) {
        additional.forEach((type, value) -> source.merge(type, value, Double::sum));
        return additional;
    }

    public Map<AttributeType, Double> getBuffAttributes(RPGCharacter<?> character) {
        return new HashMap<>(character.getBuffAttributes());
    }

    public Map<AttributeType, Double> getAllAttributes(Entity entity) {
        RPGCharacter<?> character = characterService.getOrCreateCharacter(entity);

        HashMap<AttributeType, Double> results = new HashMap<>();

        mergeAttributes(results, getDefaultAttributes());
        mergeAttributes(results, character.getCharacterAttributes());
        mergeAttributes(results, getEquipmentAttributes(entity));
        mergeAttributes(results, character.getBuffAttributes());

        results.replaceAll((key, value) -> Math.max(0.0d, value));
        return results;
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
