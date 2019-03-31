package com.atherys.rpg.facade;

import com.atherys.rpg.api.skill.DescriptionArgument;
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

    @Inject
    private ExpressionService expressionService;

    @Inject
    private AttributeFacade attributeFacade;

    @SafeVarargs
    public final Text renderSkillDescription(Living living, TextTemplate descriptionTemplate, Tuple<String, ?>... descriptionArguments) {
        Map<String, TextRepresentable> renderedArguments = new HashMap<>(descriptionArguments.length);

        for (Tuple<String, ?> argument : descriptionArguments) {

            // by default, the value of the argument is the Text of the argument value
            TextRepresentable value = Text.of(argument.getSecond());

            // if the argument is of type DescriptionArgument, pass in the living as the source and evaluate
            if (argument.getSecond() instanceof DescriptionArgument) {
                value = ((DescriptionArgument) argument.getSecond()).apply(living);
            }

            // if the argument is of type Expression, evaluate it with the user as source
            if (argument.getSecond() instanceof Expression) {
                value = Text.of(expressionService.evalExpression(living, (Expression) argument.getSecond()));
            }
            // put the rendered argument into the map
            renderedArguments.put(argument.getFirst(), value);
        }

        // Apply all rendered arguments to the template and convert to text
        return descriptionTemplate.apply(renderedArguments).toText();
    }

    public long getSkillCooldown(Living living, String cooldownExpression) {
        return expressionService.evalExpression(living, cooldownExpression).longValue();
    }

    public double getSkillResourceCost(Living living, String resourceCostExpression) {
        return expressionService.evalExpression(living, resourceCostExpression).doubleValue();
    }

}
