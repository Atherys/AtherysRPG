package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.Attribute;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class EntityCharacter<T extends Living & ArmorEquipable> implements RPGCharacter<T> {

    @Id
    private UUID id;

    @Transient
    private T entity;

    @ElementCollection // TODO
    private Set<Attribute> attributes = new HashSet<>();

    @Nonnull
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Optional<T> getEntity() {
        return Optional.ofNullable(entity);
    }

    public void setEntity(T living) {
        this.entity = living;
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }
}
