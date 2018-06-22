package com.atherys.rpg.event;

import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public abstract class CastEvent implements Event {

    private Cause cause;

    private Castable skill;
    private CastableCarrier character;
    private long timestamp;
    private String[] args;

    public CastEvent(CastableCarrier character, Castable castable, long timestamp, String... args) {
        this.cause = Cause.builder()
                .append(character)
                .append(castable)
                .append(timestamp)
                .append(args)
                .build(Sponge.getCauseStackManager().getCurrentContext());

        this.skill = castable;
        this.character = character;
        this.timestamp = timestamp;
        this.args = args;
    }

    public Castable getSkill() {
        return skill;
    }

    public CastableCarrier getCharacter() {
        return character;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public Cause getCause() {
        return cause;
    }

}
