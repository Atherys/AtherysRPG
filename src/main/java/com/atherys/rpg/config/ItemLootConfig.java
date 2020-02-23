package com.atherys.rpg.config;

import com.atherys.rpg.api.stat.AttributeType;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.EnchantmentType;

import java.util.*;

@ConfigSerializable
public class ItemLootConfig {

    @Setting("name")
    public String ITEM_NAME = "Default Item Name";

    @Setting("hide-flags")
    public boolean HIDE_FLAGS = false;

    @Setting("item-type")
    public ItemType ITEM_TYPE = ItemTypes.DIRT;

    @Setting("quantity-minimum")
    public int MINIMUM_QUANTITY = 1;

    @Setting("quantity-minimum")
    public int MAXIMUM_QUANTITY = 1;

    @Setting("lore")
    public List<String> LORE = new ArrayList<>();

    @Setting("enchantments")
    public Map<EnchantmentType, Integer> ENCHANTMENTS = new HashMap<>();

    @Setting("attributes")
    public Map<AttributeType, Double> ATTRIBUTES = new HashMap<>();

    public ItemLootConfig() {
    }

    @Override
    public String toString() {
        return "ItemLootConfig{" +
                "ITEM_NAME='" + ITEM_NAME + '\'' +
                ", HIDE_FLAGS=" + HIDE_FLAGS +
                ", ITEM_TYPE=" + ITEM_TYPE +
                ", MINIMUM_QUANTITY=" + MINIMUM_QUANTITY +
                ", MAXIMUM_QUANTITY=" + MAXIMUM_QUANTITY +
                ", LORE=" + LORE +
                ", ENCHANTMENTS=" + ENCHANTMENTS +
                ", ATTRIBUTES=" + ATTRIBUTES +
                '}';
    }
}
