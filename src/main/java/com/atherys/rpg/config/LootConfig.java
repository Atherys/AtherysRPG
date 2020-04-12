package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class LootConfig {

    @Setting("drop-rate")
    public Double DROP_RATE = 0.0d;

    @Setting("currency")
    public CurrencyLootConfig CURRENCY;

    @Setting("items")
    public ItemLootConfig ITEM;

    @Setting("experience")
    public ExperienceLootConfig EXPERIENCE;

    public LootConfig() {
    }
}
