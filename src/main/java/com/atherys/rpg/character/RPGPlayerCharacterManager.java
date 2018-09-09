package com.atherys.rpg.character;

import com.atherys.rpg.api.CharacterManager;
import com.atherys.rpg.api.character.RPGCharacter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.*;

public class RPGPlayerCharacterManager implements CharacterManager<Player, PlayerCharacter> {

    private static RPGPlayerCharacterManager instance = new RPGPlayerCharacterManager();

    private Map<UUID,PlayerCharacter> characters = new HashMap<>();

    private RPGPlayerCharacterManager() {}

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
        PlayerCharacter playerCharacter = new PlayerCharacter(uuid);
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

    public static RPGPlayerCharacterManager getInstance() {
        return instance;
    }

    @Override
    public Collection<PlayerCharacter> getOnline() {
        List<PlayerCharacter> onlinePlayerCharacters = new ArrayList<>();
        Sponge.getServer().getOnlinePlayers().forEach(player -> get(player).ifPresent(onlinePlayerCharacters::add));
        return onlinePlayerCharacters;
    }

    @Override
    public PlayerCharacter getOrCreate(UUID uuid) {
        return null;
    }

    @Override
    public Optional<PlayerCharacter> get(UUID uuid) {
        return Optional.ofNullable(characters.get(uuid));
    }

    /**
     * Creates a new PlayerCharacter, without caching the player object. Be warned, if the object returned by this
     * method is used in a capacity which requires the player object, the player object will be obtained via
     * the uuid provided here the first time, at which point it will be cached and future operations will be faster.
     *
     * @param uuid The uuid of the player
     * @return The newly created RPGCharacter
     */
    @Override
    public PlayerCharacter create(UUID uuid) {
        PlayerCharacter playerCharacter = new PlayerCharacter(uuid);
        characters.put(uuid, playerCharacter);
        return playerCharacter;
    }
}
