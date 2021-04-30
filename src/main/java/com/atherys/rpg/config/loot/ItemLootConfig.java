package com.atherys.rpg.config.loot;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class ItemLootConfig {

    @Setting("item-group")
    public String ITEM_GROUP = null;

    @Setting("item-ids")
    public List<String> ITEM_IDS = new ArrayList<>();

    @Setting("quantity-min")
    public int MINIMUM_QUANTITY = 1;

    @Setting("quantity-max")
    public int MAXIMUM_QUANTITY = 1;

    @Setting("quantity")
    public int QUANTITY = 1;

    public ItemLootConfig() {
    }
}
