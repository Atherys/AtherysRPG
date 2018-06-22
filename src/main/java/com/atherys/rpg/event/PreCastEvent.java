package com.atherys.rpg.event;

import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;
import org.spongepowered.api.event.Cancellable;

public class PreCastEvent extends CastEvent implements Cancellable {

    private boolean cancelled;

    public PreCastEvent(CastableCarrier character, Castable castable, long timestamp, String... args) {
        super(character, castable, timestamp, args);
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
