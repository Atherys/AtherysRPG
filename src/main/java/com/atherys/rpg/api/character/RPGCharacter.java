package com.atherys.rpg.api.character;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;

import java.util.Map;
import java.util.Optional;

public interface RPGCharacter<T extends Living & Equipable> extends SpongeIdentifiable {

    Optional<T> getEntity();

    void setEntity(T entity);

    Map<AttributeType, Double> getAttributes();
}
