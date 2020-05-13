package com.atherys.rpg.facade;

import com.atherys.rpg.api.skill.DescriptionArgument;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.skill.TargetedRPGSkill;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.service.ExpressionService;
import com.atherys.rpg.service.RPGCharacterService;
import com.atherys.rpg.util.TextUtils;
import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.event.SkillCastEvent;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.util.Tuple;

import java.util.*;

import static org.spongepowered.api.text.Text.NEW_LINE;
import static org.spongepowered.api.text.format.TextColors.DARK_GREEN;
import static org.spongepowered.api.text.format.TextColors.GOLD;

@Singleton
public class RPGSkillFacade {

    @Inject
    private ExpressionService expressionService;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private AttributeFacade attributeFacade;

    @Inject
    private SkillGraphFacade graphFacade;

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private RPGMessagingFacade rpgMsg;

    public void sendMessageOnSkillCast(SkillCastEvent.Post event) {

        Text message;

        if (event.getResult().getMessage() != Text.EMPTY) {
            message = event.getResult().getMessage();
        } else {
            String name;

            if (event.getUser() instanceof Player) {
                name = ((Player) event.getUser()).getName();
            } else {
                name = event.getUser().get(Keys.DISPLAY_NAME).orElse(Text.of(event.getUser().getType().getName())).toPlain();
            }

            message = rpgMsg.formatInfo(GOLD, name, DARK_GREEN, " casted ", GOLD, event.getSkill().getName(), "!");
        }

        event.getUser().getNearbyEntities(config.SKILL_MESSAGE_DISTANCE).forEach(entity -> {
            if (entity instanceof Player) {
                ((Player) entity).sendMessage(message);
            }
        });
    }

    public Text renderAvailableSkill(RPGSkill skill, Player source) {
        Text.Builder skillText = renderSkill(skill, source).toBuilder();

        Set<RPGSkill> linkedSkills = graphFacade.getLinkedSkills(Sets.newHashSet(skill));

        if (linkedSkills.isEmpty()) {
            return skillText.build();
        }

        Text.Builder hoverText = skillText.getHoverAction()
                .map(hoverAction -> (Text) hoverAction.getResult()).get()
                .toBuilder();

        List<String> skills = characterService.getOrCreateCharacter(source).getSkills();
        hoverText.append(Text.of(NEW_LINE, NEW_LINE, DARK_GREEN, "Unlock Cost: ", GOLD, graphFacade.getCostForSkill(skill, skills).get()));
        hoverText.append(Text.of(NEW_LINE, DARK_GREEN, "Unlocks: "));

        int i = 0;
        for (RPGSkill linkedSkill : linkedSkills) {
            i++;
            hoverText.append(renderSkill(linkedSkill, source));
            if (i < linkedSkills.size()) {
                hoverText.append(Text.of(GOLD, ", "));
            }
        }
        skillText.onHover(TextActions.showText(hoverText.build()));

        return skillText.build();
    }

    public Text renderSkill(RPGSkill skill, Player source) {
        Text.Builder hoverText = Text.builder().append(Text.of(GOLD, skill.getName(), Text.NEW_LINE));

        hoverText.append(Text.of(skill.getDescription(source), Text.NEW_LINE));
        hoverText.append(Text.of(NEW_LINE, DARK_GREEN, "Cooldown: ", GOLD, TextUtils.formatDuration(skill.getCooldown(source))));
        hoverText.append(Text.of(NEW_LINE, DARK_GREEN, "Cost: ", GOLD, (int) skill.getResourceCost(source)));

        if (skill instanceof TargetedRPGSkill) {
            int range = (int) ((TargetedRPGSkill) skill).getRange(source);
            hoverText.append(Text.of(NEW_LINE, DARK_GREEN, "Range: ", GOLD, range, " blocks"));
        }

        return Text.builder()
                .append(Text.of(GOLD, skill.getName()))
                .onHover(TextActions.showText(hoverText.build()))
                .build();
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
        return descriptionTemplate.apply(renderedArguments).build();
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
