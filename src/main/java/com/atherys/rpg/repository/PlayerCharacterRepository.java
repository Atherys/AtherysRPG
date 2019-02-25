package com.atherys.rpg.repository;

import com.atherys.core.db.CachedHibernateRepository;
import com.atherys.rpg.character.PlayerCharacter;
import com.google.inject.Singleton;

import java.util.UUID;

@Singleton
public class PlayerCharacterRepository extends CachedHibernateRepository<PlayerCharacter, UUID> {
    public PlayerCharacterRepository() {
        super(PlayerCharacter.class);
    }
}
