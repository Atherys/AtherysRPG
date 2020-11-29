package com.atherys.rpg.data;

import com.atherys.rpg.api.stat.AttributeType;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Map;

public class RPGKeys {

    public static Key<MapValue<AttributeType, Double>> ATTRIBUTES;

    public static Key<Value<String>> DAMAGE_EXPRESSION;

    private RPGKeys() {
    }
}
