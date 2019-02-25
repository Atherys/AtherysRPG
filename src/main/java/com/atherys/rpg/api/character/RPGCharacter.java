package com.atherys.rpg.api.character;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.rpg.api.stat.Attribute;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;

import java.util.Optional;
import java.util.Set;

public interface RPGCharacter<T extends Living & Equipable> extends SpongeIdentifiable {

    Optional<T> getEntity();

    Set<Attribute> getAttributes();

}
