package com.atherys.rpg;

import com.atherys.core.utils.RuntimeTypeAdapterFactory;
import com.atherys.rpg.api.*;
import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.resource.Resource;
import com.atherys.rpg.attribute.RPGAttributeService;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.character.RPGPlayerCharacterManager;
import com.atherys.rpg.db.RPGDatabase;
import com.atherys.rpg.gson.AtherysRPGRegistry;
import com.atherys.rpg.mutators.*;
import com.atherys.rpg.resource.ActionPoints;
import com.atherys.rpg.resource.Mana;
import com.atherys.rpg.resource.RPGResourceService;
import com.atherys.rpg.resource.Rage;
import com.atherys.rpg.skill.RPGCooldownService;
import com.atherys.rpg.skill.RPGSkillService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.io.IOException;
import java.util.Arrays;

import static com.atherys.rpg.AtherysRPG.*;

@Plugin(
        id = ID,
        name = NAME,
        description = DESCRIPTION,
        version = VERSION,
        dependencies = {
                @Dependency(id = "atheryscore")
        }
)
public class AtherysRPG {

    public static final String ID = "atherysrpg";
    public static final String NAME = "AtherysRPG";
    public static final String DESCRIPTION = "An RPG Plugin designed for the A'therys Horizons server.";
    public static final String VERSION = "1.0.0a";

    private static AtherysRPG instance;
    private static boolean init;

    private RPGConfig config;

    private AtherysRPGRegistry registry;

    private SkillService skillService;
    private AttributeService attributeService;
    private CooldownService cooldownService;
    private ResourceService resourceService;

    private CharacterManager<Player, PlayerCharacter> characterManager;

    @Inject
    Logger logger;

    private void init() {
        instance = this;

        try {
            config = new RPGConfig();
            config.init();
        } catch (IOException e) {
            e.printStackTrace();
            init = false;
            return;
        }

        if (config.IS_DEFAULT) {
            logger.error("AtherysRPG config set to default. Plugin will halt. Please modify is-default in config.conf to 'false' once non-default values have been inserted.");
            init = false;
            return;
        }

        init = true;
    }

    private void start() {
        registry = AtherysRPGRegistry.getInstance();

        skillService = RPGSkillService.getInstance();

        attributeService = RPGAttributeService.getInstance();

        cooldownService = RPGCooldownService.getInstance();

        resourceService = RPGResourceService.getInstance();

        characterManager = RPGPlayerCharacterManager.getInstance();

        AtherysRPG.getRegistry().add(Mutator.class, RuntimeTypeAdapterFactory.of(Mutator.class));
        AtherysRPG.getRegistry().registerSubtypes(Mutator.class, Arrays.asList(
                ArmorMutator.class,
                AttributeMutator.class,
                DataMutator.class,
                MaxHealthMutator.class,
                ResourceMutator.class,
                SkillMutator.class,
                WeaponMutator.class
        ));

        AtherysRPG.getRegistry().add(Resource.class, RuntimeTypeAdapterFactory.of(Resource.class));
        AtherysRPG.getRegistry().registerSubtypes(Resource.class, Arrays.asList(
                ActionPoints.class,
                Mana.class,
                Rage.class
        ));

        getPlayerCharacterManager().loadAll();

    }

    private void stop() {
        getPlayerCharacterManager().saveAll();
    }

    @Listener
    private void onInit(GameInitializationEvent event) {
        this.init();
    }

    @Listener
    private void onStart(GameStartingServerEvent event) {
        if (init) this.start();
    }

    @Listener
    private void onStop(GameStoppingServerEvent event) {
        if (init) this.stop();
    }

    public AtherysRPGRegistry registry() {
        return registry;
    }

    public SkillService skillService() {
        return skillService;
    }

    public AttributeService attributeService() { return attributeService; }

    public CooldownService cooldownService() {
        return cooldownService;
    }

    public ResourceService resourceService() {
        return resourceService;
    }

    public CharacterManager<Player, PlayerCharacter> characterManager() {
        return characterManager;
    }

    public Logger logger() {
        return logger;
    }

    private RPGConfig config() {
        return config;
    }

    String getWorkingDirectory() {
        return "config/" + ID;
    }

    public static AtherysRPGRegistry getRegistry() {
        return getInstance().registry();
    }

    public static SkillService getSkillService() {
        return getInstance().skillService();
    }

    public static AttributeService getAttributeService() { return getInstance().attributeService(); }

    public static CooldownService getCooldownService() {
        return getInstance().cooldownService();
    }

    public static ResourceService getResourceService() {
        return getInstance().resourceService();
    }

    public static CharacterManager<Player, PlayerCharacter> getPlayerCharacterManager() {
        return getInstance().characterManager();
    }

    public static Logger getLogger() {
        return getInstance().logger();
    }

    public static RPGConfig getConfig() {
        return getInstance().config();
    }

    public static RPGDatabase getDatabase() {
        return RPGDatabase.getInstance();
    }

    public static AtherysRPG getInstance() {
        return instance;
    }
}
