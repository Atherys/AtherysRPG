package com.atherys.rpg.service;

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
    private ExpressionService expressionService;

    public DamageService() {
    }

    public double getMeleeDamage(
            Map<AttributeType, Double> attackerAttributes,
            Map<AttributeType, Double> targetAttributes,
            ItemType weaponType
    ) {
        String damageType = getMeleeDamageType(weaponType);

        // Calculate and return the damage
        return calcDamage(attackerAttributes, targetAttributes, damageType);
    }

    public double getRangedDamage(
            Map<AttributeType, Double> attackerAttributes,
            Map<AttributeType, Double> targetAttributes,
            EntityType projectileType,
            double speed
    ) {
        String damageType = getRangedDamageType(projectileType);

        // Calculate and return the damage
        return calcRangedDamage(attackerAttributes, targetAttributes, damageType, speed);
    }

    public double calcRangedDamage(
            Map<AttributeType, Double> attackerAttributes,
            Map<AttributeType, Double> targetAttributes,
            String type,
            double speed
    ) {
        Expression producedDamageExpression = expressionService.getExpression(config.DAMAGE_CALCULATIONS.get(type));

        producedDamageExpression.setVariable("SPEED", BigDecimal.valueOf(speed));
        expressionService.populateSourceAttributes(producedDamageExpression, attackerAttributes);
        double producedDamage = producedDamageExpression.eval().doubleValue();

        return getPhysicalDamageMitigation(targetAttributes, producedDamage);
    }

    public double calcDamage(Map<AttributeType, Double> attackerAttributes, Map<AttributeType, Double> targetAttributes, String type) {
        Expression producedDamageExpression = expressionService.getExpression(config.DAMAGE_CALCULATIONS.get(type));

        expressionService.populateSourceAttributes(producedDamageExpression, attackerAttributes);
        double producedDamage = producedDamageExpression.eval().doubleValue();

        return getPhysicalDamageMitigation(targetAttributes, producedDamage);
    }

    public double getPhysicalDamageMitigation(Map<AttributeType, Double> targetAttributes, double producedDamage) {
        Expression mitigatedDamageExpression = expressionService.getExpression(config.PHYSICAL_DAMAGE_MITIGATION_CALCULATION);

        expressionService.populateTargetAttributes(mitigatedDamageExpression, targetAttributes);

        return mitigatedDamageExpression.setVariable(INCOMING, new BigDecimal(producedDamage)).eval().doubleValue();
    }

    public double getMagicalDamageMitigation(Map<AttributeType, Double> targetAttributes, double producedDamage) {
        Expression mitigatedDamageExpression = expressionService.getExpression(config.MAGICAL_DAMAGE_MITIGATION_CALCULATION);

        expressionService.populateTargetAttributes(mitigatedDamageExpression, targetAttributes);

        return mitigatedDamageExpression.setVariable(INCOMING, new BigDecimal(producedDamage)).eval().doubleValue();
    }

    public double getDamageFromExpression(Map<AttributeType, Double> attackerAttributes,
                                          Map<AttributeType, Double> targetAttributes,
                                          String damageExpression) {
        return getDamageFromExpression(attackerAttributes, targetAttributes, damageExpression, null);
    }

    public double getDamageFromExpression(Map<AttributeType, Double> attackerAttributes,
                                          Map<AttributeType, Double> targetAttributes,
                                          String damageExpression,
                                          Map<String, BigDecimal> extraAttributes) {
        Expression customExpression = expressionService.getExpression(damageExpression);

        if (extraAttributes != null) {
            extraAttributes.forEach(customExpression::setVariable);
        }

        expressionService.populateSourceAttributes(customExpression, attackerAttributes);
        expressionService.populateTargetAttributes(customExpression, targetAttributes);

        return customExpression.eval().doubleValue();
    }

    public String getMeleeDamageExpression(ItemType itemType) {
        String type = getMeleeDamageType(itemType);
        return config.DAMAGE_CALCULATIONS.get(type);
    }

    private String getMeleeDamageType(ItemType itemType) {
        return config.ITEM_DAMAGE_TYPES.getOrDefault(itemType, config.DEFAULT_MELEE_TYPE);
    }

    public String getRangedDamageExpression(EntityType projectileType) {
        String type = getRangedDamageType(projectileType);
        return config.DAMAGE_CALCULATIONS.get(type);
    }

    private String getRangedDamageType(EntityType projectileType) {
        return config.PROJECTILE_DAMAGE_TYPES.getOrDefault(projectileType, config.DEFAULT_RANGED_TYPE);
    }
}
