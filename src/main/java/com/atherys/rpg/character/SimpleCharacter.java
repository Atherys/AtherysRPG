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

    private Map<AttributeType, Double> characterAttributes;

    private Map<AttributeType, Double> buffAttributes;

    public SimpleCharacter(T entity, Map<AttributeType, Double> characterAttributes) {
        this.id = entity.getUniqueId();
        this.entity = entity;
        this.characterAttributes = characterAttributes;
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
    public Map<AttributeType, Double> getCharacterAttributes() {
        return characterAttributes;
    }

    @Override
    public void setCharacterAttribute(AttributeType type, Double value) {
        characterAttributes.put(type, value);
    }

    @Override
    public void addCharacterAttribute(AttributeType type, Double amount) {
        characterAttributes.merge(type, amount, Double::sum);
    }

    public void setCharacterAttributes(Map<AttributeType, Double> characterAttributes) {
        this.characterAttributes = characterAttributes;
    }

    @Override
    public Map<AttributeType, Double> getBuffAttributes() {
        return buffAttributes;
    }

    @Override
    public void mergeBuffAttributes(Map<AttributeType, Double> additional) {
        additional.forEach((type, value) -> buffAttributes.merge(type, value, Double::sum));
    }

}
