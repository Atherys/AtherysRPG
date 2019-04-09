package com.atherys.rpg.api.skill;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.util.ConversionUtils;
import com.atherys.skills.api.skill.Castable;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.util.Tuple;

import java.util.Map;

public abstract class RPGSkill implements Castable {

    private String id;

    private String name;

    private String permission;

    private TextTemplate descriptionTemplate;

    private Tuple<String, ?>[] descriptionArguments;

    private String cooldownExpression;

    private String resourceCostExpression;

    private Map<String,String> properties;

    protected RPGSkill(SkillSpec skillSpec) {
        this.id = skillSpec.getId();
        this.name = skillSpec.getName();
        this.permission = skillSpec.getPermission();
        this.descriptionTemplate = skillSpec.getDescriptionTemplate();
        this.descriptionArguments = skillSpec.getDescriptionArguments();
        this.cooldownExpression = skillSpec.getCooldownExpression();
        this.resourceCostExpression = skillSpec.getResourceCostExpression();
        this.properties = skillSpec.getProperties();
    }

    protected static Expression asExpression(String expression) {
        return AtherysRPG.getInstance().getExpressionService().getExpression(expression);
    }

    protected static double asDouble(Living source, String exp) {
        return AtherysRPG.getInstance().getExpressionService().evalExpression(source, exp).doubleValue();
    }

    protected static double asDouble(Living source, Living target, String exp) {
        return AtherysRPG.getInstance().getExpressionService().evalExpression(source, target, exp).doubleValue();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Text getDescription(Living living) {
        return AtherysRPG.getInstance().getRPGSkillFacade().renderSkillDescription(living, descriptionTemplate, descriptionArguments);
    }

    @Override
    public long getCooldown(Living living) {
        return AtherysRPG.getInstance().getRPGSkillFacade().getSkillCooldown(living, cooldownExpression);
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public double getResourceCost(Living living) {
        return AtherysRPG.getInstance().getRPGSkillFacade().getSkillResourceCost(living, resourceCostExpression);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getProperty(String propertyKey, Class<T> asClass, T defaultValue) {
        if (String.class.equals(asClass)) {
            return (T) properties.get(propertyKey);
        }

        if (Integer.class.equals(asClass)) {
            return (T) ConversionUtils.valueOf(properties.get(propertyKey), (Integer) defaultValue);
        }

        if (Double.class.equals(asClass)) {
            return (T) ConversionUtils.valueOf(properties.get(propertyKey), (Double) defaultValue);
        }

        if (Float.class.equals(asClass)) {
            return (T) ConversionUtils.valueOf(properties.get(propertyKey), (Float) defaultValue);
        }

        if (Long.class.equals(asClass)) {
            return (T) ConversionUtils.valueOf(properties.get(propertyKey), (Long) defaultValue);
        }

        if (Byte.class.equals(asClass)) {
            return (T) ConversionUtils.valueOf(properties.get(propertyKey), (Byte) defaultValue);
        }

        if (Short.class.equals(asClass)) {
            return (T) ConversionUtils.valueOf(properties.get(propertyKey), (Short) defaultValue);
        }

        return defaultValue;
    }
}