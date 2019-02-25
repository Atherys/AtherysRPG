package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypes;
import com.atherys.rpg.sources.AtherysDamageSources;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.item.ItemType;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class DamageService {

    @Inject
    private AtherysRPGConfig config;

    private Map<String, Expression> cachedExpressions = new HashMap<>();

    public DamageService() {
    }

    public void damageMelee(RPGCharacter<?> attacker, RPGCharacter<?> target) {
        target.getEntity().ifPresent((t) -> {
            double damage = 0.0;
            AtherysDamageType damageType = AtherysDamageTypes.UNARMED;
            Equipable attackingEntity = attacker.getEntity().orElse(null);

            if (attackingEntity == null) {
                throw new IllegalStateException("Attacking entity cannot be null");
            }

            // TODO

            t.damage(damage, AtherysDamageSources.melee(damageType, (Living) attackingEntity).build());
        });
        // TODO
    }

    public void damageRanged(RPGCharacter<?> attacker, RPGCharacter<?> target, ItemType itemType) {
        // TODO
    }

    public void damageMagic(RPGCharacter<?> attacker, RPGCharacter<?> target, AtherysDamageType damageType) {
        // TODO
    }

    public double calcDamage(RPGCharacter<?> attacker, RPGCharacter<?> target, DamageType type) {
        Expression expression = getExpression(config.DAMAGE_CALCULATIONS.get(type));

        // TODO

        return 0.0d;
    }

    public double calcHealthRegen() {
        Expression expression = getExpression(config.HEALTH_REGEN_CALCULATION);

        // TODO

        return 0.0d;
    }

    public double calcResourceRegen() {
        Expression expression = getExpression(config.RESOURCE_REGEN_CALCULATION);

        // TODO

        return 0.0d;
    }

    private Expression getExpression(String expression) {
        Expression result = cachedExpressions.get(expression);

        if (result == null) {
            result = new Expression(expression);
            cachedExpressions.put(expression, result);
        }

        return result;
    }

    private AtherysDamageType getMeleeDamageType(ItemType itemType) {
        return config.MELEE_ITEM_DAMAGE_TYPES.getOrDefault(itemType, AtherysDamageTypes.UNARMED);
    }
}
