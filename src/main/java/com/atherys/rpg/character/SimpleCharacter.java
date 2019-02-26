package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.Attribute;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import java.util.*;

public class SimpleCharacter<T extends Living & Equipable> implements RPGCharacter<T> {

    private UUID id;

    private T entity;

    private Map<AttributeType, Double> attributes = new HashMap<>();

    public SimpleCharacter(T entity, Map<AttributeType, Double> attributes) {
        this.id = entity.getUniqueId();
        this.entity = entity;
        this.attributes = attributes;
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
    public Map<AttributeType, Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, Double> attributes) {
        this.attributes = attributes;
    }
}
