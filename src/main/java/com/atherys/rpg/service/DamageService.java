package com.atherys.rpg.service;

import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypes;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;

import java.math.BigDecimal;
import java.util.Map;

@Singleton
public class DamageService {

    private static final String INCOMING = "INCOMING";

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private AttributeService attributeService;

    @Inject
    private ExpressionService expressionService;

    public DamageService() {
    }

    public double getMeleeDamage(
            Map<AttributeType, Double> attackerAttributes,
            Map<AttributeType, Double> targetAttributes,
            ItemType weaponType
    ) {
        AtherysDamageType damageType = getMeleeDamageType(weaponType);

        // Calculate and return the damage
        return calcDamage(attackerAttributes, targetAttributes, damageType);
    }

    public double getRangedDamage(
            Map<AttributeType, Double> attackerAttributes,
            Map<AttributeType, Double> targetAttributes,
            EntityType projectileType
    ) {
        AtherysDamageType damageType = getRangedDamageType(projectileType);

        // Calculate and return the damage
        return calcDamage(attackerAttributes, targetAttributes, damageType);
    }

    public double calcDamage(Map<AttributeType, Double> attackerAttributes, Map<AttributeType, Double> targetAttributes, AtherysDamageType type) {
        Expression producedDamageExpression = expressionService.getExpression(config.DAMAGE_CALCULATIONS.get(type));

        expressionService.populateAttributes(producedDamageExpression, attackerAttributes, "source");
        double producedDamage = producedDamageExpression.eval().doubleValue();

        return getPhysicalDamageMitigation(targetAttributes, producedDamage);
    }

    public double getPhysicalDamageMitigation(Map<AttributeType, Double> targetAttributes, double producedDamage) {
        Expression mitigatedDamageExpression = expressionService.getExpression(config.PHYSICAL_DAMAGE_MITIGATION_CALCULATION);

        expressionService.populateAttributes(mitigatedDamageExpression, targetAttributes, "source");

        return mitigatedDamageExpression.setVariable(INCOMING, new BigDecimal(producedDamage)).eval().doubleValue();
    }

    public double getMagicalDamageMitigation(Map<AttributeType, Double> targetAttributes, double producedDamage) {
        Expression mitigatedDamageExpression = expressionService.getExpression(config.MAGICAL_DAMAGE_MITIGATION_CALCULATION);

        expressionService.populateAttributes(mitigatedDamageExpression, targetAttributes, "source");

        return mitigatedDamageExpression.setVariable(INCOMING, new BigDecimal(producedDamage)).eval().doubleValue();
    }

    private AtherysDamageType getMeleeDamageType(ItemType itemType) {
        return config.ITEM_DAMAGE_TYPES.getOrDefault(itemType, AtherysDamageTypes.UNARMED);
    }

    private AtherysDamageType getRangedDamageType(EntityType projectileType) {
        return config.PROJECTILE_DAMAGE_TYPES.getOrDefault(projectileType, AtherysDamageTypes.PIERCE);
    }
}
