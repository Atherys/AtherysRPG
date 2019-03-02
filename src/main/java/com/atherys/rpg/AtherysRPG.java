package com.atherys.rpg;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.core.event.AtherysHibernateConfigurationEvent;
import com.atherys.core.event.AtherysHibernateInitializedEvent;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypeRegistry;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.api.stat.AttributeTypeRegistry;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.AttributesCommand;
import com.atherys.rpg.command.ExperienceCommand;
import com.atherys.rpg.facade.RPGCharacterFacade;
import com.atherys.rpg.facade.RPGMessagingFacade;
import com.atherys.rpg.listener.EntityListener;
import com.atherys.rpg.listener.SkillsListener;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "atherysrpg",
        name = "A'therys RPG",
        description = "An RPG plugin for the A'therys Horizons server",
        version = "1.0.0a",
        dependencies = {
                @Dependency(id = "atheryscore"),
                @Dependency(id = "atherysskills")
        }
)
public class AtherysRPG {

    private static AtherysRPG instance;

    private static boolean init = false;

    @Inject
    private Logger logger;

    @Inject
    private Injector spongeInjector;

    private Components components;

    private void init() {
        instance = this;

        components = new Components();

        // Register custom CatalogType registry modules
        Sponge.getRegistry().registerModule(AttributeType.class, new AttributeTypeRegistry());
        Sponge.getRegistry().registerModule(AtherysDamageType.class, new AtherysDamageTypeRegistry());

        // Create injector
        Injector rpgInjector = spongeInjector.createChildInjector(new AtherysRPGModule());
        rpgInjector.injectMembers(components);

        // Initialize the config
        getConfig().init();

        // Register listeners
        Sponge.getEventManager().registerListeners(this, components.entityListener);
        Sponge.getEventManager().registerListeners(this, components.skillsListener);

        // Register commands
        try {
            AtherysCore.getCommandService().register(new AttributesCommand(), this);
            AtherysCore.getCommandService().register(new ExperienceCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }

        init = true;
    }

    private void start() {
        getPlayerCharacterRepository().initCache();
    }

    private void stop() {
        getPlayerCharacterRepository().flushCache();
    }

    @Listener
    public void onHibernateConfiguration(AtherysHibernateConfigurationEvent event) {
        event.registerEntity(PlayerCharacter.class);
    }

    @Listener
    public void onInit(AtherysHibernateInitializedEvent event) {
        init();
    }

    @Listener
    public void onStart(GameStartingServerEvent event) {
        if (init) {
            start();
        }
    }

    @Listener
    public void onStop(GameStoppingServerEvent event) {
        if (init) {
            stop();
        }
    }

    private static class Components {
        @Inject
        AtherysRPGConfig config;

        @Inject
        PlayerCharacterRepository playerCharacterRepository;

        @Inject
        AttributeService attributeService;

        @Inject
        DamageService damageService;

        @Inject
        RPGCharacterService characterService;

        @Inject
        RPGMessagingFacade rpgMessagingFacade;

        @Inject
        RPGCharacterFacade rpgCharacterFacade;

        @Inject
        EntityListener entityListener;

        @Inject
        SkillsListener skillsListener;
    }

    public static AtherysRPG getInstance() {
        return instance;
    }

    public AtherysRPGConfig getConfig() {
        return components.config;
    }

    public PlayerCharacterRepository getPlayerCharacterRepository() {
        return components.playerCharacterRepository;
    }

    public AttributeService getAttributeService() {
        return components.attributeService;
    }

    public DamageService getDamageService() {
        return components.damageService;
    }

    public RPGCharacterService getCharacterService() {
        return components.characterService;
    }

    public RPGMessagingFacade getRPGMessagingFacade() {
        return components.rpgMessagingFacade;
    }

    public RPGCharacterFacade getRPGCharacterFacade() {
        return components.rpgCharacterFacade;
    }
}
