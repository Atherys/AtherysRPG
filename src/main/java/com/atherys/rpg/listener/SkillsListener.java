package com.atherys.rpg.listener;

import com.atherys.rpg.facade.RPGCharacterFacade;
import com.atherys.rpg.facade.RPGSkillFacade;
import com.atherys.skills.api.event.ResourceEvent;
import com.atherys.skills.api.event.SkillCastEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;

@Singleton
public class SkillsListener {

    @Inject
    private RPGCharacterFacade rpgCharacterFacade;

    @Inject
    private RPGSkillFacade skillFacade;

    @Listener
    public void onResourceRegen(ResourceEvent.Regen event, @Root Player player) {
        rpgCharacterFacade.onResourceRegen(event, player);
    }

    @Listener
    public void onSkillCast(SkillCastEvent.Post event) {
        skillFacade.sendMessageOnSkillCast(event);
    }
}
