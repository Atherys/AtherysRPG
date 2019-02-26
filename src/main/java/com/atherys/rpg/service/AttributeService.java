package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.stat.AttributeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class AttributeService {

    @Inject
    private AtherysRPGConfig config;

    public AttributeService() {}

    public Map<AttributeType, Double> getDefaultAttributes() {
        return new HashMap<>(config.DEFAULT_ATTRIBUTES);
    }

    public Map<AttributeType, Double> getItemStackAttributes(ItemStack stack) {
        // TODO: Fetch AttributeData from the itemstack and get the attribute values from it
        return new HashMap<>();
    }

    public Map<AttributeType, Double> getOffhandAttributes(Equipable equipable) {
        return equipable.getEquipped(EquipmentTypes.OFF_HAND).map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getMainHandAttributes(Equipable equipable) {
        return equipable.getEquipped(EquipmentTypes.MAIN_HAND).map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getHelmetAttributes(ArmorEquipable equipable) {
        return equipable.getHelmet().map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getChestplateAttributes(ArmorEquipable equipable) {
        return equipable.getChestplate().map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getLeggingsAttributes(ArmorEquipable equipable) {
        return equipable.getLeggings().map(this::getItemStackAttributes).orElse(new HashMap<>());
    }

    public Map<AttributeType, Double> getBootsAttributes(ArmorEquipable equipable) {
        return equipable.getBoots().map(this::getItemStackAttributes).orElse(new HashMap<>());
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
     * @param source The map to be altered
     * @param additional The additional attributes to be added
     * @return The altered source map
     */
    public Map<AttributeType, Double> mergeAttributes(Map<AttributeType, Double> source, Map<AttributeType, Double> additional) {
        additional.forEach((type, value) -> source.merge(type, value, (v1, v2) -> v1 + v2));
        return additional;
    }
}
