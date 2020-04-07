package com.atherys.rpg.api.event;

import com.atherys.rpg.character.PlayerCharacter;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;

public class ChangeAttributeEvent extends AbstractEvent {

    private PlayerCharacter pc;

    public ChangeAttributeEvent(PlayerCharacter pc) {
        this.pc = pc;
    }

    @Override
    public Cause getCause() {
        return Cause.of(EventContext.empty(), pc);
    }
}
