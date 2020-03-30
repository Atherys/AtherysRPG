package com.atherys.rpg.config;

import com.atherys.rpg.api.stat.AttributeType;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.EnchantmentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigSerializable
public class ItemConfig {

    @Setting("id")
    public String ID = "unique-item-id";

    @Setting("name")
    public String ITEM_NAME = "&oDefault Item Name";

    @Setting("hide-flags")
    public boolean HIDE_FLAGS = false;

    @Setting("type")
    public ItemType ITEM_TYPE = ItemTypes.DIRT;

    @Setting("durability")
    public int DURABILITY = -1; // if durability is -1, that means full default item durability

    @Setting("lore")
    public List<String> LORE = new ArrayList<>();

    @Setting("enchantments")
    public Map<EnchantmentType, Integer> ENCHANTMENTS = new HashMap<>();

    @Setting("attributes")
    public Map<AttributeType, Double> ATTRIBUTES = new HashMap<>();

}
