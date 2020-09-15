package com.atherys.rpg.repository;

import com.atherys.core.db.CachedHibernateRepository;
import com.atherys.rpg.character.PlayerCharacter;
import com.google.inject.Singleton;

import javax.persistence.Query;
import java.util.UUID;

@Singleton
public class PlayerCharacterRepository extends CachedHibernateRepository<PlayerCharacter, UUID> {
    public PlayerCharacterRepository() {
        super(PlayerCharacter.class);
    }

    public void fetchAndCachePlayerCharacter(UUID playerUUID) {
        querySingleAsync(
                "SELECT pc FROM PlayerCharacter pc WHERE pc.id = :uuid",
                PlayerCharacter.class, (Query q) -> {
                    q.setParameter("uuid", playerUUID);
                },
                (result) -> {
                    if (result.isPresent()) {
                        cache.set(playerUUID, result.get());
                    } else {
                        cache.removeById(playerUUID);
                    }
                }
        );
    }
}
