package com.atherys.rpg.api.character;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.effect.Applyable;
import org.spongepowered.api.data.manipulator.DataManipulator;

import java.util.*;

public abstract class AbstractRPGCharacter implements RPGCharacter {

    private Map<String, Applyable> effects = new HashMap<>();

    @Override
    public List<Attribute> getAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        asLiving().ifPresent(living -> {
            for (DataManipulator<?, ?> value : living.getContainers()) {
                if (value instanceof Attribute) {
                    attributes.add((Attribute) value);
                }
            }
        });
        return attributes;
    }

    @Override
    public <T extends Attribute> void addAttribute(T attribute) {
        // TODO: Create ListData of Attributes and retrieve/store to that instead for better performance
        //asLiving().ifPresent(living -> living.offer(attribute));
    }

    @Override
    public Optional<? extends Attribute> getAttributeById(String id) {
        for (Attribute attribute : getAttributes()) {
            if (attribute.getId().equals(id)) return Optional.of(attribute);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Applyable> getEffects() {
        return effects.values();
    }

    @Override
    public Optional<? extends Applyable> getAppliedEffectById(String id) {
        return Optional.ofNullable(effects.get(id));
    }

    @Override
    public <T extends Applyable> boolean apply(T effect, long timestamp) {
        if (getEffects().contains(effect)) return false;
        if (effect.canApply(timestamp, this)) {
            effect.apply(timestamp, this);
            return true;
        } else return false;
    }

    @Override
    public <T extends Applyable> boolean remove(T effect, long timestamp) {
        if (!getEffects().contains(effect)) return false;
        if (effect.canRemove(timestamp, this)) {
            effect.remove(this);
            return true;
        } else return false;
    }

    @Override
    public void drainResource(double amount) {
        getResource().drain(amount);
    }

    @Override
    public void fillResource(double amount) {
        getResource().fill(amount);
    }
}
