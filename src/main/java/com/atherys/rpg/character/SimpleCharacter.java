package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.Attribute;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class SimpleCharacter<T extends Living & Equipable> implements RPGCharacter<T> {

    private UUID id;

    private T entity;

    private Set<Attribute> attributes = new HashSet<>();

    public SimpleCharacter(T entity, Set<Attribute> attributes) {
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
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
