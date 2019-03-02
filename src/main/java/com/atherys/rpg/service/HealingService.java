package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;

@Singleton
public class HealingService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private ExpressionService expressionService;

    public double getHealthRegenAmount(RPGCharacter<?> target) {
        return calcHealthRegen(target);
    }

    public void heal(Living entity, double amount) {
        entity.transform(Keys.HEALTH, (value) -> value + amount);
    }

    private double calcHealthRegen(RPGCharacter<?> source) {
        Expression expression = expressionService.getExpression(config.HEALTH_REGEN_CALCULATION);

        expressionService.populateAttributes(expression, source, "source");

        return expression.eval().doubleValue();
    }

}
