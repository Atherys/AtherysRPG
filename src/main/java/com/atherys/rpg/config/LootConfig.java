package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class LootConfig {

    @Setting("drop-rate")
    public Double DROP_RATE = 0.0d;

    @Setting("currency")
    public CurrencyLootConfig CURRENCY;

    @Setting("item")
    public ItemLootConfig ITEMS;

    @Setting("experience")
    public ExperienceLootConfig EXPERIENCE;

    public LootConfig() {
    }
}
