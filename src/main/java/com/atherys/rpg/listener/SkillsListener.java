package com.atherys.rpg.listener;

import com.atherys.rpg.facade.RPGCharacterFacade;
import com.atherys.skills.api.event.ResourceRegenEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;

@Singleton
public class SkillsListener {

    @Inject
    private RPGCharacterFacade rpgCharacterFacade;

    @Listener
    public void onResourceRegen(ResourceRegenEvent event, @Root Player player) {
        rpgCharacterFacade.onResourceRegen(event, player);
    }

}
