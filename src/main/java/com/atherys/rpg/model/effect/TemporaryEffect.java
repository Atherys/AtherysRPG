package com.atherys.rpg.model.effect;

import com.atherys.rpg.api.effect.ApplyableCarrier;

public abstract class TemporaryEffect extends AbstractEffect {

    private long timestampApplied;
    private long duration;

    private boolean applied;

    protected TemporaryEffect(String id, String name, long duration) {
        super(id, name);
        this.duration = duration;
        this.applied = false;
    }

    @Override
    public <T extends ApplyableCarrier> boolean canApply(long timestamp, T character) {
        return !applied;
    }

    @Override
    public <T extends ApplyableCarrier> boolean apply(long timestamp, T character) {
        this.timestampApplied = timestamp;
        this.applied = true;
        return apply(character);
    }

    @Override
    public <T extends ApplyableCarrier> boolean canRemove(long timestamp, T character) {
        return timestampApplied + duration < timestamp && applied;
    }

    @Override
    public <T extends ApplyableCarrier> boolean remove(long timestamp, T character) {
        return remove(character);
    }

    protected abstract <T extends ApplyableCarrier> boolean apply(T character);

    protected abstract <T extends ApplyableCarrier> boolean remove(T character);
}
