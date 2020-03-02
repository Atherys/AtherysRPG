package com.atherys.rpg;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.core.event.AtherysHibernateConfigurationEvent;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypeRegistry;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.api.stat.AttributeTypeRegistry;
import com.atherys.rpg.api.stat.AttributeTypes;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.ExperienceCommand;
import com.atherys.rpg.command.attribute.AttributesCommand;
import com.atherys.rpg.command.skill.SkillsCommand;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.config.MobsConfig;
import com.atherys.rpg.config.SkillGraphConfig;
import com.atherys.rpg.data.AttributeData;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.data.RPGKeys;
import com.atherys.rpg.facade.*;
import com.atherys.rpg.listener.EntityListener;
import com.atherys.rpg.listener.SkillsListener;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.atherys.rpg.service.*;
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
        version = "%PLUGIN_VERSION%",
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
        getGraphConfig().init();
        getMobsConfig().init();

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

        init = true;
    }

    private void start() {
        getPlayerCharacterRepository().initCache();
        components.healingService.init();
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
        RPGKeys.DAMAGE_EXPRESSION = Key.builder()
                .id("atherys:damage_expression")
                .name("Damage Expression")
                .query(DataQuery.of("Damage_Expression"))
                .type(new TypeToken<Value<String>>() {})
                .build();

        RPGKeys.DEXTERITY= createKey(AttributeTypes.DEXTERITY, "Dexterity");

        RPGKeys.CONSTITUTION = createKey(AttributeTypes.CONSTITUTION, "Constitution");

        RPGKeys.INTELLIGENCE = createKey(AttributeTypes.INTELLIGENCE, "Intelligence");

        RPGKeys.STRENGTH = createKey(AttributeTypes.STRENGTH, "Strength");

        RPGKeys.WISDOM = createKey(AttributeTypes.WISDOM, "Wisdom");

        RPGKeys.MAGICAL_RESISTANCE = createKey(AttributeTypes.MAGICAL_RESISTANCE, "Magical_Resistance");

        RPGKeys.PHYSICAL_RESISTANCE = createKey(AttributeTypes.PHYSICAL_RESISTANCE, "Physical_Resistance");

        RPGKeys.BASE_ARMOR = createKey(AttributeTypes.BASE_ARMOR, "Base_Armor");

        RPGKeys.BASE_DAMAGE = createKey(AttributeTypes.BASE_DAMAGE, "Base_Damage");
    }

    private static Key<Value<Double>> createKey(AttributeType attributeType, String dataQuery) {
        return Key.builder()
                .id(attributeType.getId())
                .name(attributeType.getName())
                .query(DataQuery.of(dataQuery))
                .type(new TypeToken<Value<Double>>() {})
                .build();
    }

    @Listener
    public void onDataRegistration(GameRegistryEvent.Register<DataRegistration<?, ?>> event) {
        // Register custom data
        DataRegistration.builder()
                .dataClass(DamageExpressionData.class)
                .immutableClass(DamageExpressionData.Immutable.class)
                .builder(new DamageExpressionData.Builder())
                .dataName("DamageExpression")
                .manipulatorId("damageExpression")
                .buildAndRegister(container);

        DataRegistration.builder()
                .dataClass(AttributeData.class)
                .immutableClass(AttributeData.Immutable.class)
                .builder(new AttributeData.Builder())
                .dataName("Attributes")
                .manipulatorId("attributes")
                .buildAndRegister(container);
    }

    public Logger getLogger() {
        return logger;
    }

    public AtherysRPGConfig getConfig() {
        return components.config;
    }

    public SkillGraphConfig getGraphConfig() {
        return components.skillGraphConfig;
    }

    public MobsConfig getMobsConfig() {
        return components.mobsConfig;
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
        SkillGraphConfig skillGraphConfig;

        @Inject
        MobsConfig mobsConfig;

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
        HealingService healingService;

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
