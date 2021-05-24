package com.atherys.rpg.skill.description;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.skill.DescriptionArgument;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.stat.AttributeType;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;

import java.math.BigDecimal;
import java.util.Map;

public class PropertyDescriptionArgument implements DescriptionArgument {
    protected RPGSkill skill;
    protected String propertyName;
    protected String defaultValue;

    public PropertyDescriptionArgument(RPGSkill skill, String propertyName, String defaultValue) {
        this.skill = skill;
        this.propertyName = propertyName;
        this.defaultValue = defaultValue;
    }

    @Override
    public TextRepresentable apply(Living living) {
        double value = evalExpression(living).doubleValue();
        return Text.of(String.format("%.2f", value));
    }

    public BigDecimal evalExpression(Living source) {
        String expressionString = skill.getProperty(propertyName, String.class, defaultValue);
        Expression expression = AtherysRPG.getInstance().getExpressionService().getExpression(expressionString);

        // Fill in TARGET attributes so the expression can still be viewed in descriptions
        Map<AttributeType, Double> defaultAttributes = AtherysRPG.getInstance().getAttributeService().getDefaultAttributes();
        AtherysRPG.getInstance().getExpressionService().populateTargetAttributes(expression, defaultAttributes);

        return AtherysRPG.getInstance().getExpressionService().evalExpression(source, expression);
    }

    public long asLong(Living living) {
        String expression = skill.getProperty(propertyName, String.class, defaultValue);
        return AtherysRPG.getInstance().getExpressionService().evalExpression(living, expression).longValue();
    }
}
