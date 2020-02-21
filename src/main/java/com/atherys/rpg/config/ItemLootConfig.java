package com.atherys.rpg.config;

import com.atherys.rpg.api.stat.AttributeType;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.item.enchantment.EnchantmentType;

import java.util.*;

@ConfigSerializable
public class ItemLootConfig {

    @Setting("name")
    public String ITEM_NAME = "Default Item Name";

    @Setting("lore")
    public Set<String> LORE = new HashSet<>();

    @Setting("enchantments")
    public Map<EnchantmentType, Integer> ENCHANTMENTS = new HashMap<>();

    @Setting("attributes")
    public Map<AttributeType, Double> ATTRIBUTES = new HashMap<>();

    public ItemLootConfig() {
    }
}
