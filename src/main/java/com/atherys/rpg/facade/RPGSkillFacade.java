package com.atherys.rpg.facade;

import com.atherys.rpg.api.skill.DescriptionArgument;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.service.ExpressionService;
import com.atherys.skills.AtherysSkills;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class RPGSkillFacade {

    @Inject
    private ExpressionService expressionService;

    @Inject
    private AttributeFacade attributeFacade;

    public Text renderSkill(RPGSkill skill, Player source) {
        Text.Builder skillText = Text.builder().append(Text.of(TextColors.GOLD, skill.getName()));
        skillText.append(Text.of(" - ", skill.getDescription(source)));
        skillText.append(Text.of(Text.NEW_LINE, "Cooldown: ", DurationFormatUtils.formatDuration(skill.getCooldown(source), "HH'h' mm'm' ss's'")));
        skillText.append(Text.of(Text.NEW_LINE, "Cost: ", skill.getResourceCost(source)));

        return skillText.build();
    }

    @SafeVarargs
    public final Text renderSkillDescription(Living living, TextTemplate descriptionTemplate, Tuple<String, ?>... descriptionArguments) {
        if (descriptionArguments == null) {
            return descriptionTemplate.toText();
        }

        Map<String, TextRepresentable> renderedArguments = new HashMap<>();

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

    public Optional<RPGSkill> getSkillById(String skillId) {
        return AtherysSkills.getInstance().getSkillService().getSkillById(skillId)
                .filter(skill -> skill instanceof RPGSkill)
                .map(skill -> (RPGSkill) skill);
    }
}
