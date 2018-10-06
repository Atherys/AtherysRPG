package com.atherys.rpg.event;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public abstract class CastEvent implements Event {

    private Cause cause;

    protected Castable skill;
    protected CastableCarrier character;
    protected long timestamp;
    protected String[] args;

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

    public static class Pre extends CastEvent implements Cancellable {

        private boolean cancelled = false;

        public Pre(CastableCarrier character, Castable castable, long timestamp, String... args) {
            super(character, castable, timestamp, args);
        }

        public void setSkill(Castable skill) {
            super.skill = skill;
        }

        public void setCharacter(CastableCarrier character) {
            super.character = character;
        }

        public void setTimestamp(long timestamp) {
            super.timestamp = timestamp;
        }

        public void setArgs(String[] args) {
            super.args = args;
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

    public static class Post extends CastEvent {

        private CastResult result;

        public Post(CastableCarrier character, Castable castable, CastResult result, long timestamp, String... args) {
            super(character, castable, timestamp, args);
            this.result = result;
        }

        public CastResult getResult() {
            return result;
        }
    }

}
