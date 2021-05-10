package com.atherys.rpg.api.character;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;

import java.util.Map;
import java.util.Optional;

/**
 * Represents an entity that can have attributes
 * @param <T> The type of Entity
 */
public interface RPGCharacter<T extends Living & Equipable> extends SpongeIdentifiable {

    Optional<T> getEntity();

    void setEntity(T entity);

    /**
     * Gets the current Character Attributes
     * @return Map of Character Attributes
     */
    Map<AttributeType, Double> getCharacterAttributes();

    /**
     * Set a Character attribute to a specific value
     * @param type Attribute to set
     * @param value Value to set to
     */
    void setCharacterAttribute(AttributeType type, Double value);

    /**
     * Add a value to an existing character attribute
     * @param type Attribute to add
     * @param amount Amount to add
     */
    void addCharacterAttribute(AttributeType type, Double amount);

    /**
     * Gets the current Buffed Attributes
     * @return Map of Attributes provided by buffs
     */
    Map<AttributeType, Double> getBuffAttributes();

    /**
     * Merge the values of the two attribute type maps by adding them together
     * @param additional Attributes to add
     */
    void mergeBuffAttributes(Map<AttributeType, Double> additional);

}
