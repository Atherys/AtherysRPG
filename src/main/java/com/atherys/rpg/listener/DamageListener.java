package com.atherys.rpg.listener;

import com.atherys.rpg.facade.RPGCharacterFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

@Singleton
public class DamageListener {

    @Inject
    private RPGCharacterFacade characterFacade;

    @Listener
    public void onDamage(DamageEntityEvent event, @Root EntityDamageSource source) {
        characterFacade.onDamage(event, source);
    }

}
