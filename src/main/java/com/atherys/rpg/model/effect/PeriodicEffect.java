package com.atherys.rpg.model.effect;

import com.atherys.rpg.api.effect.ApplyableCarrier;

public abstract class PeriodicEffect extends AbstractEffect {

    private long lastApplied;

    private long timeBetween;
    private int ticksRemaining;

    protected PeriodicEffect(String id, String name, long timeBetweenTicks, int ticks) {
        super(id, name);
        this.timeBetween = timeBetweenTicks;
        this.ticksRemaining = ticks;
    }

    @Override
    public <T extends ApplyableCarrier> boolean canApply(long timestamp, T character) {
        return lastApplied + timeBetween < timestamp && ticksRemaining > 0;
    }

    @Override
    public <T extends ApplyableCarrier> boolean apply(long timestamp, T character) {
        lastApplied = timestamp;
        boolean result = apply(character);
        if (result) ticksRemaining--;
        return result;
    }

    @Override
    public <T extends ApplyableCarrier> boolean canRemove(long timestamp, T character) {
        return ticksRemaining == 0;
    }

    @Override
    public <T extends ApplyableCarrier> boolean remove(long timestamp, T character) {
        return remove(character);
    }

    protected abstract <T extends ApplyableCarrier> boolean apply(T character);

    protected abstract <T extends ApplyableCarrier> boolean remove(T character);
}
