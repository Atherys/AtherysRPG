package com.atherys.rpg.listener;

import com.atherys.rpg.facade.RPGCharacterFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.event.entity.DamageEntityEvent;

@Singleton
public class DamageListener {

    @Inject
    private RPGCharacterFacade characterFacade;

    public void onDamage(DamageEntityEvent event) {
        characterFacade.onDamage(event);
    }

}
