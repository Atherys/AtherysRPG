package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypes;
import com.atherys.rpg.sources.AtherysDamageSources;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.ItemType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DamageService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private AttributeService attributeService;

    @Inject
    private ExpressionService expressionService;

    public DamageService() {
    }

    public double getMeleeDamage(RPGCharacter<?> attacker, RPGCharacter<?> target, ItemType weaponType) {
        AtherysDamageType damageType = getMeleeDamageType(weaponType);

        // TODO: Account for items

        // Calculate and return the damage
        return calcDamage(attacker, target, damageType);
    }

    public double getRangedDamage(RPGCharacter<?> attacker, RPGCharacter<?> target, EntityType projectileType) {
        AtherysDamageType damageType = getRangedDamageType(projectileType);

        // TODO: Account for items

        // Calculate and return the damage
        return calcDamage(attacker, target, damageType);
    }

    public double getMagicDamage(RPGCharacter<?> attacker, RPGCharacter<?> target, AtherysDamageType damageType) {

        // TODO: Account for items?

        // Calculate and return the damage
        return calcDamage(attacker, target, damageType);
    }

    public double calcDamage(RPGCharacter<?> attacker, RPGCharacter<?> target, AtherysDamageType type) {
        Expression expression = expressionService.getExpression(config.DAMAGE_CALCULATIONS.get(type));

        expressionService.populateAttributes(expression, attacker, "source");
        expressionService.populateAttributes(expression, target, "target");

        return expression.eval().doubleValue();
    }

    public double calcResourceRegen(RPGCharacter<?> source) {
        Expression expression = expressionService.getExpression(config.RESOURCE_REGEN_CALCULATION);

        expressionService.populateAttributes(expression, source, "source");

        return expression.eval().doubleValue();
    }

    private AtherysDamageType getMeleeDamageType(ItemType itemType) {
        return config.ITEM_DAMAGE_TYPES.getOrDefault(itemType, AtherysDamageTypes.UNARMED);
    }

    private AtherysDamageType getRangedDamageType(EntityType projectileType) {
        return config.PROJECTILE_DAMAGE_TYPES.getOrDefault(projectileType, AtherysDamageTypes.PIERCE);
    }

    private Living getAsLiving(RPGCharacter<? extends Living> character) {
        Living entity = character.getEntity().orElse(null);

        if (entity == null) {
            throw new IllegalStateException("Entity cannot be null.");
        }

        return entity;
    }

    private Equipable getAsEquipable(RPGCharacter<? extends Equipable> character) {
        Equipable entity = character.getEntity().orElse(null);

        if (entity == null) {
            throw new IllegalStateException("Entity cannot be null.");
        }

        return entity;
    }
}
