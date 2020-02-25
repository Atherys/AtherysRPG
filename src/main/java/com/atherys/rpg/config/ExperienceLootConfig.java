package com.atherys.rpg.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class ExperienceLootConfig {

    @Setting("minimum")
    public Double MINIMUM = 100.0;

    @Setting("maximum")
    public Double MAXIMUM = 200.0;

    public ExperienceLootConfig() {
    }
}
