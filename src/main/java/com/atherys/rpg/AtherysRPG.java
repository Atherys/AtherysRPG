package com.atherys.rpg;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.core.event.AtherysHibernateConfigurationEvent;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypeRegistry;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.api.stat.AttributeTypeRegistry;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.AttributesCommand;
import com.atherys.rpg.command.ExperienceCommand;
import com.atherys.rpg.command.skill.SkillsCommand;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.AttributeData;
import com.atherys.rpg.data.AttributeKeys;
import com.atherys.rpg.facade.*;
import com.atherys.rpg.listener.EntityListener;
import com.atherys.rpg.listener.SkillsListener;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.ExpressionService;
import com.atherys.rpg.service.RPGCharacterService;
import com.atherys.rpg.skill.DummySkill;
import com.atherys.rpg.skill.RPGSimpleDamageSkill;
import com.atherys.skills.AtherysSkills;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

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

    @Inject
    private PluginContainer container;

    private Components components;

    public static AtherysRPG getInstance() {
        return instance;
    }

    private void init() {
        instance = this;

        components = new Components();
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
            AtherysCore.getCommandService().register(new SkillsCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }

        registerSkills();

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

    @Listener(order = Order.LATE)
    public void onInit(GameInitializationEvent event) {
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

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        // Register custom CatalogType registry modules
        Sponge.getRegistry().registerModule(AttributeType.class, new AttributeTypeRegistry());
        Sponge.getRegistry().registerModule(AtherysDamageType.class, new AtherysDamageTypeRegistry());
    }

    @Listener
    public void onKeyRegistration(GameRegistryEvent.Register<Key<?>> event) {
        final TypeToken<Value<Double>> doubleToken = new TypeToken<Value<Double>>() {
        };

        AttributeKeys.AGILITY = Key.builder()
                .id("agility")
                .name("Agility")
                .query(DataQuery.of("Agility"))
                .type(doubleToken)
                .build();

        AttributeKeys.CONSTITUTION = Key.builder()
                .id("constitution")
                .name("Constitution")
                .query(DataQuery.of("Constitution"))
                .type(doubleToken)
                .build();

        AttributeKeys.CHARISMA = Key.builder()
                .id("charisma")
                .name("Charisma")
                .query(DataQuery.of("Charisma"))
                .type(doubleToken)
                .build();

        AttributeKeys.DEFENSE = Key.builder()
                .id("defense")
                .name("Defense")
                .query(DataQuery.of("Defense"))
                .type(doubleToken)
                .build();

        AttributeKeys.INTELLIGENCE = Key.builder()
                .id("intelligence")
                .name("Intelligence")
                .query(DataQuery.of("Intelligence"))
                .type(doubleToken)
                .build();

        AttributeKeys.LUCK = Key.builder()
                .id("luck")
                .name("Luck")
                .query(DataQuery.of("Luck"))
                .type(doubleToken)
                .build();

        AttributeKeys.PERCEPTION = Key.builder()
                .id("perception")
                .name("Perception")
                .query(DataQuery.of("Perception"))
                .type(doubleToken)
                .build();

        AttributeKeys.STRENGTH = Key.builder()
                .id("strength")
                .name("Strength")
                .query(DataQuery.of("Strength"))
                .type(doubleToken)
                .build();

        AttributeKeys.WILLPOWER = Key.builder()
                .id("willpower")
                .name("Willpower")
                .query(DataQuery.of("Willpower"))
                .type(doubleToken)
                .build();

        AttributeKeys.WISDOM = Key.builder()
                .id("wisdom")
                .name("Wisdom")
                .query(DataQuery.of("Wisdom"))
                .type(doubleToken)
                .build();
    }

    @Listener
    public void onDataRegistration(GameRegistryEvent.Register<DataRegistration<?, ?>> event) {
        // Register custom data
        DataRegistration.builder()
                .dataClass(AttributeData.class)
                .immutableClass(AttributeData.Immutable.class)
                .builder(new AttributeData.Builder())
                .dataName("Attributes")
                .manipulatorId("attributes")
                .buildAndRegister(container);
    }

    private void registerSkills() {
        AtherysSkills.getInstance().getSkillService().registerSkills(
                new RPGSimpleDamageSkill(),
                // Dummy skills for testing
                new DummySkill("skill-1", "Skill 1", "A skill"),
                new DummySkill("skill-2", "Skill 2", "A skill"),
                new DummySkill("skill-3", "Skill 3", "A skill"),
                new DummySkill("skill-4", "Skill 4", "A skill"),
                new DummySkill("skill-5", "Skill 5", "A skill")
        );
    }

    public Logger getLogger() {
        return logger;
    }

    public AtherysRPGConfig getConfig() {
        return components.config;
    }

    public PlayerCharacterRepository getPlayerCharacterRepository() {
        return components.playerCharacterRepository;
    }

    public ExpressionService getExpressionService() {
        return components.expressionService;
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

    public AttributeFacade getAttributeFacade() {
        return components.attributeFacade;
    }

    public RPGSkillFacade getRPGSkillFacade() {
        return components.rpgSkillFacade;
    }

    public SkillGraphFacade getSkillGraphFacade() {
        return components.skillGraphFacade;
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
        ExpressionService expressionService;

        @Inject
        RPGMessagingFacade rpgMessagingFacade;

        @Inject
        RPGCharacterFacade rpgCharacterFacade;

        @Inject
        SkillGraphFacade skillGraphFacade;

        @Inject
        RPGSkillFacade rpgSkillFacade;

        @Inject
        AttributeFacade attributeFacade;

        @Inject
        EntityListener entityListener;

        @Inject
        SkillsListener skillsListener;
    }
}
