package com.atherys.rpg.facade;

import com.atherys.rpg.service.ExpressionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.util.Tuple;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class RPGSkillFacade {

    private static final String SKILL_SOURCE_NAME = "source";

    private static final String SKILL_TARGET_NAME = "target";

    @Inject
    private ExpressionService expressionService;

    @Inject
    private AttributeFacade attributeFacade;

    @SafeVarargs
    public final Text renderSkillDescription(Living living, TextTemplate descriptionTemplate, Tuple<String, ?>... descriptionArguments) {
        Map<String, TextRepresentable> renderedArguments = new HashMap<>(descriptionArguments.length);

        for (Tuple<String, ?> argument : descriptionArguments) {

            TextRepresentable value = null;

            // If the description argument is an expression, populate it with the entity's attributes and evaluate
            if (argument.getSecond() instanceof Expression) {
                Expression expression = (Expression) argument.getSecond();
                expressionService.populateAttributes(expression, attributeFacade.getAllAttributes(living), SKILL_SOURCE_NAME);
                value = Text.of(expression.eval().doubleValue());
            }

            // put the rendered argument into the map
            renderedArguments.put(argument.getFirst(), value);
        }

        // Apply all rendered arguments to the template and convert to text
        return descriptionTemplate.apply(renderedArguments).toText();
    }

    public long getSkillCooldown(Living living, String cooldownExpression) {
        Expression expression = expressionService.getExpression(cooldownExpression);
        expressionService.populateAttributes(expression, attributeFacade.getAllAttributes(living), SKILL_SOURCE_NAME);
        return expression.eval().longValue();
    }

    public double getSkillResourceCost(Living living, String resourceCostExpression) {
        Expression expression = expressionService.getExpression(resourceCostExpression);
        expressionService.populateAttributes(expression, attributeFacade.getAllAttributes(living), SKILL_SOURCE_NAME);
        return expression.eval().doubleValue();
    }

}
