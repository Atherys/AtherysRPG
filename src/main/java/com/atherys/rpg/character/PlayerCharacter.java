package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.repository.converter.AttributeTypeConverter;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Entity
public class PlayerCharacter implements RPGCharacter<Player> {

    @Id
    private UUID id;

    @Transient
    private Player entity;

    @Convert(attributeName = "key.", converter = AttributeTypeConverter.class)
    @ElementCollection
    @MapKeyColumn(name = "attribute_type")
    @Column(name = "value")
    @CollectionTable(name = "playercharacter_attributes")
    private Map<AttributeType, Double> attributes = new HashMap<>();

    public PlayerCharacter(Player entity, Map<AttributeType, Double> attributes) {
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
    public Optional<Player> getEntity() {
        return Optional.ofNullable(entity);
    }

    public void setEntity(Player living) {
        this.entity = living;
    }

    @Override
    public Map<AttributeType, Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, Double> attributes) {
        this.attributes = attributes;
    }

}
