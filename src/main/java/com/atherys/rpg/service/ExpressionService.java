package com.atherys.rpg.service;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ExpressionService {

    @Inject
    private AttributeService attributeService;

    private Map<String, Expression> cachedExpressions = new HashMap<>();

    public Expression getExpression(String expression) {
        Expression result = cachedExpressions.get(expression);

        if (result == null) {
            result = new Expression(expression);
            cachedExpressions.put(expression, result);
        }

        return result;
    }

    public void populateAttributes(Expression expression, RPGCharacter<?> character, String name) {
        String pattern = name.toUpperCase() + "_%s";
        attributeService.getDefaultAttributes().forEach((type, defaultValue) -> expression.setVariable(
                String.format(pattern, type.getId().toUpperCase()),
                BigDecimal.valueOf(character.getAttributes().getOrDefault(type, defaultValue))
        ));
    }

    public void populateAttributes(Expression expression, Map<AttributeType, Double> attributes, String name) {
        String pattern = name.toUpperCase() + "_%s";
        attributes.forEach((type, value) -> expression.setVariable(
                String.format(pattern, type.getId().toUpperCase()),
                BigDecimal.valueOf(value)
        ));
    }

    public double getDifference(String expression, Map<String, String> from, Map<String, String> to) {
        Expression exp = getExpression(expression);

        from.forEach(exp::setVariable);
        double fromResult = exp.eval().doubleValue();

        to.forEach(exp::setVariable);
        double toResult = exp.eval().doubleValue();

        return toResult - fromResult;
    }

}
