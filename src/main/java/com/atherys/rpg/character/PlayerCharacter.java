package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.*;

@Entity
public class PlayerCharacter implements RPGCharacter<Player> {

    @Id
    private UUID id;

    @Transient
    private Player entity;

    @ElementCollection // TODO
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
