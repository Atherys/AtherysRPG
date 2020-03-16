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
public class ItemLootConfig {
    @Setting("item-id")
    public String ITEM_ID;

    @Setting("quantity-minimum")
    public int MINIMUM_QUANTITY = 1;

    @Setting("quantity-minimum")
    public int MAXIMUM_QUANTITY = 1;

    public ItemLootConfig() {
    }
}
