package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.expression.ClampFunction;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.living.Living;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ExpressionService {

    private Map<String, Expression> cachedExpressions = new HashMap<>();

    public Expression getExpression(String expression) {
        Expression result = cachedExpressions.get(expression);

        if (result == null) {
            result = new Expression(expression);

            result.addFunction(new ClampFunction());

            cachedExpressions.put(expression, result);
        }

        return result;
    }

    public void populateAttributes(Expression expression, Map<AttributeType, Double> attributes, String name) {
        String pattern = name.toUpperCase() + "_%s";
        attributes.forEach((type, value) -> expression.setVariable(
                String.format(pattern, type.getShortName().toUpperCase()),
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

    public BigDecimal evalExpression(Living source, String stringExpression) {
        return evalExpression(source, getExpression(stringExpression));
    }

    public BigDecimal evalExpression(Living source, Living target, String stringExpression) {
        return evalExpression(source, target, getExpression(stringExpression));
    }

    public BigDecimal evalExpression(Living source, Expression expression) {
        AtherysRPG.getInstance().getExpressionService().populateAttributes(
                expression,
                AtherysRPG.getInstance().getAttributeFacade().getAllAttributes(source),
                "source"
        );

        return expression.eval(true);
    }

    public BigDecimal evalExpression(Living source, Living target, Expression expression) {
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

        return expression.eval(true);
    }
}
