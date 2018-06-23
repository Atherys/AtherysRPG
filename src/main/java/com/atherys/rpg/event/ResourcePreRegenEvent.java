package com.atherys.rpg.event;

import com.atherys.rpg.api.character.RPGCharacter;
import org.spongepowered.api.event.Cancellable;

public class ResourcePreRegenEvent extends ResourceRegenEvent implements Cancellable {

    private boolean cancelled;

    public ResourcePreRegenEvent(RPGCharacter character, double amount) {
        super(character, amount);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
