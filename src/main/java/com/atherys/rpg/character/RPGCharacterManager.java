package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class RPGCharacterManager {

    private static RPGCharacterManager instance = new RPGCharacterManager();

    private Map<UUID,RPGCharacter> characters = new HashMap<>();

    private RPGCharacterManager() {}

    public RPGCharacter getOrCreatePlayer(UUID uuid) {
        return getPlayer(uuid).orElse(createPlayer(uuid));
    }

    public RPGCharacter getOrCreatePlayer(Player player) {
        return getPlayer(player).orElse(createPlayer(player));
    }

    public Optional<RPGCharacter> getPlayer(UUID uuid) {
        return Optional.ofNullable(characters.get(uuid));
    }

    public Optional<RPGCharacter> getPlayer(Player player) {
        return Optional.ofNullable(characters.get(player.getUniqueId()));
    }

    /**
     * Creates a new PlayerCharacter, without caching the player object. Be warned, if the object returned by this
     * method is used in a capacity which requires the player object, the player object will be obtained via
     * the uuid provided here the first time, at which point it will be cached and future operations will be faster.
     *
     * @param uuid The uuid of the player
     * @return The newly created RPGCharacter
     */
    public RPGCharacter createPlayer(UUID uuid) {
        RPGCharacter playerCharacter = new PlayerCharacter(uuid);
        characters.put(uuid, playerCharacter);
        return playerCharacter;
    }

    /**
     * Creates a new PlayerCharacter, with caching the player object
     *
     * @param player
     * @return
     */
    public RPGCharacter createPlayer(Player player) {
        PlayerCharacter playerCharacter = new PlayerCharacter(player.getUniqueId());
        playerCharacter.setCachedPlayer(player);
        characters.put(player.getUniqueId(), playerCharacter);
        return playerCharacter;
    }

    public static RPGCharacterManager getInstance() {
        return instance;
    }

}
