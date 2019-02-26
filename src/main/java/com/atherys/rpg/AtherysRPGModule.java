package com.atherys.rpg;

import com.atherys.rpg.facade.RPGCharacterFacade;
import com.atherys.rpg.listener.DamageListener;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AtherysRPGModule extends AbstractModule {
    @Override
    protected void configure() {
        // Config
        bind(AtherysRPGConfig.class).in(Scopes.SINGLETON);

        // Repository
        bind(PlayerCharacterRepository.class).in(Scopes.SINGLETON);

        // Services
        bind(AttributeService.class).in(Scopes.SINGLETON);
        bind(DamageService.class).in(Scopes.SINGLETON);
        bind(RPGCharacterService.class).in(Scopes.SINGLETON);

        // Facades
        bind(RPGCharacterFacade.class).in(Scopes.SINGLETON);

        // Listeners
        bind(DamageListener.class).in(Scopes.SINGLETON);
    }
}
