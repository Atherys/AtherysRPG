package com.atherys.rpg.api.skill;

import com.atherys.rpg.AtherysRPG;
import com.atherys.skills.api.skill.Castable;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.util.Tuple;

public abstract class RPGSkill implements Castable {

    private String id;

    private String name;

    private String permission;

    private TextTemplate descriptionTemplate;

    private Tuple<String, ?>[] descriptionArguments;

    private String cooldownExpression;

    private String resourceCostExpression;

    protected RPGSkill() {
    }

    protected void id(String id) {
        this.id = id;
    }

    protected void name(String name) {
        this.name = name;
    }

    protected void permission(String permission) {
        this.permission = permission;
    }

    @SafeVarargs
    protected final void description(TextTemplate template, Tuple<String, ?>... arguments) {
        this.descriptionTemplate = template;
        this.descriptionArguments = arguments;
    }

    protected void cooldown(String expression) {
        this.cooldownExpression = expression;
    }

    protected void resourceCost(String expression) {
        this.resourceCostExpression = expression;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public Text getDescription(Living living) {
        return AtherysRPG.getInstance().getRPGSkillFacade().renderSkillDescription(living, descriptionTemplate, descriptionArguments);
    }

    @Override
    public long getCooldown(Living living) {
        return AtherysRPG.getInstance().getRPGSkillFacade().getSkillCooldown(living, cooldownExpression);
    }

    protected String getCooldownExpression() {
        return cooldownExpression;
    }

    @Override
    public double getResourceCost(Living living) {
        return AtherysRPG.getInstance().getRPGSkillFacade().getSkillResourceCost(living, resourceCostExpression);
    }

    protected String getResourceCostExpression() {
        return resourceCostExpression;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    protected Expression asExpression(String expression) {
        return AtherysRPG.getInstance().getExpressionService().getExpression(expression);
    }

    protected double asDouble(String exp, Living source) {
        Expression expression = AtherysRPG.getInstance().getExpressionService().getExpression(exp);
        AtherysRPG.getInstance().getExpressionService().populateAttributes(
                expression,
                AtherysRPG.getInstance().getAttributeFacade().getAllAttributes(source),
                "source"
        );

        return expression.eval().doubleValue();
    }

    protected double asDouble(String exp, Living source, Living target) {
        Expression expression = AtherysRPG.getInstance().getExpressionService().getExpression(exp);

        AtherysRPG.getInstance().getExpressionService().populateAttributes(
                expression,
                AtherysRPG.getInstance().getAttributeFacade().getAllAttributes(source),
                "source"
        );

        AtherysRPG.getInstance().getExpressionService().populateAttributes(
                expression,
                AtherysRPG.getInstance().getAttributeFacade().getAllAttributes(target),
                "target"
        );

        return expression.eval().doubleValue();
    }
}