package com.atherys.rpg.api.event;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.service.MobService;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;

public class RegisterMobEvent extends AbstractEvent {
    private MobService mobService;

    public RegisterMobEvent(MobService mobService) {
        this.mobService = mobService;
    }

    public void registerMob(String id, EntityArchetype mob) {
        mobService.registerMob(id, mob);
    }

    @Override
    public Cause getCause() {
        return Cause.of(EventContext.empty(), AtherysRPG.getInstance());
    }
}
