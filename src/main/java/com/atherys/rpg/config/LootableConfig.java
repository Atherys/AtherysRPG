package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.time.Duration;

@ConfigSerializable
public class LootableConfig {

    @Setting("id")
    public String ID = "lootable-id";

    @Setting("loot")
    public ItemLootConfig LOOT = new ItemLootConfig();

    @Setting("number-of-items")
    public int NUMBER_OF_ITEMS = 3;

    @Setting("min-number-of-items")
    public int MINIMUM_NUMBER_OF_ITEMS = -1;

    @Setting("max-number-of-items")
    public int MAXIMUM_NUMBER_OF_ITEMS = -1;

    @Setting("time-limit-seconds")
    public int TIME_LIMIT_IN_SECONDS = 0;


}
