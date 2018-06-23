package com.atherys.rpg.event;

import com.atherys.rpg.api.character.RPGCharacter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public abstract class ResourceRegenEvent implements Event {

    private Cause cause;

    private RPGCharacter character;
    private double amount;

    protected ResourceRegenEvent(RPGCharacter character, double amount) {
        this.cause = Cause.builder()
                .append(character)
                .build(Sponge.getCauseStackManager().getCurrentContext());
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
