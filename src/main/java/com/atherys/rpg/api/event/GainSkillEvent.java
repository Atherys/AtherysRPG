package com.atherys.rpg.api.event;

import com.atherys.rpg.api.skill.RPGSkill;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

public class GainSkillEvent extends AbstractEvent implements TargetPlayerEvent {
    private Player targetPlayer;
    private RPGSkill skillGained;

    public GainSkillEvent(Player player, RPGSkill skill) {
        this.targetPlayer = player;
        this.skillGained = skill;
    }

    @Override
    public Cause getCause() {
        return Cause.of(EventContext.empty(), targetPlayer);
    }

    @Override
    public Player getTargetEntity() {
        return targetPlayer;
    }

    public RPGSkill getSkill() {
        return skillGained;
    }
}
