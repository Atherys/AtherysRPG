package com.atherys.rpg;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.event.cause.entity.damage.DamageType;

import java.util.Map;

public class RPGKeys {

    public static Key<BaseValue<Map<DamageType,Double>>> DAMAGE_BONUSES;

    public static Key<BaseValue<Map<DamageType,Double>>> DAMAGE_RESISTS;

}
