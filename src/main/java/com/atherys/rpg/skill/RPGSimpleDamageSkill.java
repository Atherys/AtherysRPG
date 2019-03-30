package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.TargetedRPGSkill;
import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastResult;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.util.Tuple;

public class RPGSimpleDamageSkill extends TargetedRPGSkill {

    private static final String DAMAGE_EXPRESSION = "CLAMP(SOURCE_WILLPOWER * 1.5, 0.5, 10.0)";

    private static final String COOLDOWN_EXPRESSION = "6000";

    private static final String RESOURCE_EXPRESSION = "12.0";

    public RPGSimpleDamageSkill() {
        id("rpg-simple-damage");
        name("RPG Simple Damage");
        description(
                TextTemplate.of(
                        "A simple damage skill which deals ", TextTemplate.arg("damage").build(),
                        "damage, costs ", TextTemplate.arg("resource").build(),
                        " and has a ", TextTemplate.arg("cooldown").build(), " cooldown."
                ),
                Tuple.of("damage", asExpression(DAMAGE_EXPRESSION)),
                Tuple.of("cooldown", asExpression(COOLDOWN_EXPRESSION)),
                Tuple.of("resource", asExpression(RESOURCE_EXPRESSION))
        );
        cooldown(COOLDOWN_EXPRESSION);
        resourceCost(RESOURCE_EXPRESSION);
    }

    @Override
    public CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException {

        double damage = asDouble(DAMAGE_EXPRESSION, user, target);

        if (user instanceof MessageReceiver) {
            MessageReceiver receiver = (MessageReceiver) user;
            receiver.sendMessage(Text.of("Using Skill: ", getName()));
            receiver.sendMessage(Text.of("Description: ", getDescription(user)));
            receiver.sendMessage(Text.of("Cooldown: ", getCooldown(user)));
            receiver.sendMessage(Text.of("Resource Cost: ", getResourceCost(user)));
            receiver.sendMessage(Text.of("Dealing ", damage, " damage to ", target.getType().getName()));
        }

        target.damage(damage, DamageSources.VOID);

        return CastResult.success();
    }
}
