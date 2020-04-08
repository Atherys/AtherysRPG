package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class LootConfig {

    @Setting("currency")
    public CurrencyLootConfig CURRENCY;

    @Setting("items")
    public List<ItemLootConfig> ITEMS;

    @Setting("experience")
    public ExperienceLootConfig EXPERIENCE;

    @Setting("item-drop-limit")
    public int ITEM_DROP_LIMIT = 2;

    public LootConfig() {
    }
}
