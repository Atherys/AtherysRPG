package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;

@Singleton
public class HealingService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private ExpressionService expressionService;

//    public double getHealthRegenAmount(RPGCharacter<?> target) {
//        return calcHealthRegen(target);
//    }

    public void heal(Living entity, double amount) {
        entity.transform(Keys.HEALTH, (value) -> value + amount);
    }

}
