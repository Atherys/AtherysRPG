package com.atherys.rpg.character;

import com.atherys.core.database.mongo.AbstractMongoDatabaseManager;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.CharacterManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.*;

public class RPGPlayerCharacterManager extends AbstractMongoDatabaseManager<PlayerCharacter> implements CharacterManager<Player, PlayerCharacter> {

    private static RPGPlayerCharacterManager instance = new RPGPlayerCharacterManager();

    protected RPGPlayerCharacterManager() {
        super(AtherysRPG.getLogger(), AtherysRPG.getDatabase(), PlayerCharacter.class);
    }

    @Override
    public Collection<PlayerCharacter> getOnline() {
        List<PlayerCharacter> onlinePlayerCharacters = new ArrayList<>();
        Sponge.getServer().getOnlinePlayers().forEach(player -> get(player).ifPresent(onlinePlayerCharacters::add));
        return onlinePlayerCharacters;
    }

    @Override
    public Optional<PlayerCharacter> get(UUID uuid) {
        return Optional.ofNullable(getCache().get(uuid));
    }

    @Override
    public Optional<PlayerCharacter> get(Player living) {
        return get(living.getUniqueId()).map(pc -> {
            pc.setCachedPlayer(living);
            return pc;
        });
    }

    @Override
    public PlayerCharacter create(UUID uuid) {
        PlayerCharacter playerCharacter = new PlayerCharacter(uuid);
        save(playerCharacter);
        return playerCharacter;
    }

    @Override
    public PlayerCharacter create(Player living) {
        PlayerCharacter playerCharacter = new PlayerCharacter(living.getUniqueId());
        playerCharacter.setCachedPlayer(living);
        save(playerCharacter);
        return playerCharacter;
    }

    @Override
    public void saveAll() {
        saveAll(getCache().values());
    }

    public static RPGPlayerCharacterManager getInstance() {
        return instance;
    }
}
