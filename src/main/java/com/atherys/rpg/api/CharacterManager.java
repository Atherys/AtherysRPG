package com.atherys.rpg.api;

import com.atherys.core.database.api.DatabaseManager;
import com.atherys.rpg.api.character.RPGCharacter;
import org.spongepowered.api.entity.living.Living;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * A CharacterManager is responsible for handling all RPGCharacter objects relating to a type of Living
 * An example might be CharacterManager<Player> whose job would be to manage all RPGCharacter objects belonging to players.
 * @param <T>
 */
public interface CharacterManager<T extends Living, V extends RPGCharacter> extends DatabaseManager<V> {

    Collection<V> getOnline();

    default V getOrCreate(UUID uuid) {
        return get(uuid).orElse(create(uuid));
    }

    Optional<V> get(UUID uuid);

    V create(UUID uuid);

    default V getOrCreate(T living) {
        return get(living).orElse(create(living));
    }

    default Optional<V> get(T living) {
        return get(living.getUniqueId());
    }

    default V create(T living) {
        return create(living.getUniqueId());
    }

    void saveAll();

}
