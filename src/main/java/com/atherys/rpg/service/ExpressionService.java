package com.atherys.rpg.service;

import com.atherys.rpg.api.character.RPGCharacter;
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

}
