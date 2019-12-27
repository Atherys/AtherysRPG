package com.atherys.rpg.data;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;

public class RPGKeys {

    public static Key<Value<String>> DAMAGE_EXPRESSION;

    public static Key<Value<Double>> DEXTERITY;

    public static Key<Value<Double>> CONSTITUTION;

    public static Key<Value<Double>> INTELLIGENCE;

    public static Key<Value<Double>> STRENGTH;

    public static Key<Value<Double>> WISDOM;

    public static Key<Value<Double>> MAGICAL_RESISTANCE;

    public static Key<Value<Double>> PHYSICAL_RESISTANCE;

    public static Key<Value<Double>> BASE_ARMOR;

    public static Key<Value<Double>> BASE_DAMAGE;

    private RPGKeys() {
    }
}
