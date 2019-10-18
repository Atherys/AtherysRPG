package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SimpleCharacter<T extends Living & Equipable> implements RPGCharacter<T> {

    private UUID id;

    private T entity;

    private Map<AttributeType, Double> baseAttributes;

    private Map<AttributeType, Double> buffAttributes;

    public SimpleCharacter(T entity, Map<AttributeType, Double> baseAttributes) {
        this.id = entity.getUniqueId();
        this.entity = entity;
        this.baseAttributes = baseAttributes;
        this.buffAttributes = new HashMap<>();
    }

    @Nonnull
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Optional<T> getEntity() {
        return Optional.ofNullable(entity);
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public Map<AttributeType, Double> getBaseAttributes() {
        return baseAttributes;
    }

    public void setBaseAttributes(Map<AttributeType, Double> baseAttributes) {
        this.baseAttributes = baseAttributes;
    }

    @Override
    public Map<AttributeType, Double> getBuffAttributes() {
        return buffAttributes;
    }

    public void setBuffAttributes(Map<AttributeType, Double> buffAttributes) {
        this.buffAttributes = baseAttributes;
    }
}
