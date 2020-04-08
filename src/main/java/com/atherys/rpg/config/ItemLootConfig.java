package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ItemLootConfig {

    @Setting("drop-rate")
    public Double DROP_RATE = 0.0d;

    @Setting("item-id")
    public String ITEM_ID;

    @Setting("quantity-minimum")
    public int MINIMUM_QUANTITY = 1;

    @Setting("quantity-minimum")
    public int MAXIMUM_QUANTITY = 1;

    public ItemLootConfig() {
    }
}
