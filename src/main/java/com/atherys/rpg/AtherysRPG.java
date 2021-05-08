package com.atherys.rpg;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.core.event.AtherysDatabaseMigrationEvent;
import com.atherys.core.event.AtherysHibernateConfigurationEvent;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypeRegistry;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.api.stat.AttributeTypeRegistry;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.ExperienceCommand;
import com.atherys.rpg.command.SpawnItemCommand;
import com.atherys.rpg.command.attribute.AttributesCommand;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.command.skill.SkillsCommand;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.config.archetype.ArchetypesConfig;
import com.atherys.rpg.config.loot.ItemTemplatesConfig;
import com.atherys.rpg.config.mob.MobsConfig;
import com.atherys.rpg.config.skill.SkillGraphConfig;
import com.atherys.rpg.config.stat.AttributesConfig;
import com.atherys.rpg.data.AttributeMapData;
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
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.channel.MessageReceiver;

@Plugin(
        id = "atherysrpg",
        name = "A'therys RPG",
        description = "An RPG plugin for the A'therys Horizons server",
        version = "%PROJECT_VERSION%",
        dependencies = {
                @Dependency(id = "atheryscore"),
                @Dependency(id = "atherysskills"),
                @Dependency(id = "atherysparties", optional = true)
        }
)
public class AtherysRPG {

    private static AtherysRPG instance;

    private static boolean init = false;

    @Inject
    private Logger logger;

    @Inject
    private Injector spongeInjector;

    private Injector rpgInjector;

    @Inject
    private PluginContainer container;

    private Components components;

    public static AtherysRPG getInstance() {
        return instance;
    }

    private void init() {
        instance = this;

        components = new Components();
        rpgInjector = spongeInjector.createChildInjector(new AtherysRPGModule());
        rpgInjector.injectMembers(components);

        // Initialize the config
        getConfig().init();
        getGraphConfig().init();
        getMobsConfig().init();
        getArchetypesConfig().init();

        getItemFacade().init();

        // Register listeners
        Sponge.getEventManager().registerListeners(this, components.entityListener);
        Sponge.getEventManager().registerListeners(this, components.skillsListener);

        // Register commands
        try {
            AtherysCore.getCommandService().register(new AttributesCommand(), this);
            AtherysCore.getCommandService().register(new ExperienceCommand(), this);
            AtherysCore.getCommandService().register(new SkillsCommand(), this);
            AtherysCore.getCommandService().register(new SpawnItemCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }

        init = true;
    }

    private void start() {
        getPlayerCharacterRepository().initCache();
        components.healingService.init();
    }

    private void reload(Cause cause) {
        getConfig().load();
        getGraphConfig().load();
        getMobsConfig().load();
        getArchetypesConfig().load();

        getItemFacade().init();

        // Re-register command to update item choices
        try {
            Sponge.getCommandManager()
                    .get("spawnitem")
                    .ifPresent(Sponge.getCommandManager()::removeMapping);

            AtherysCore.getCommandService().register(new SpawnItemCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }

        try {
            getSkillGraphFacade().resetSkillGraph();
        } catch (RPGCommandException e) {
            if (cause.root() instanceof MessageReceiver) {
                ((MessageReceiver) cause.root()).sendMessage(e.getText());
            }
        }
    }

    private void stop() {
        getPlayerCharacterRepository().flushCache();
    }

    @Listener
    public void onHibernateConfiguration(AtherysHibernateConfigurationEvent event) {
        event.registerEntity(PlayerCharacter.class);
    }

    @Listener
    public void onDatabaseMigration(AtherysDatabaseMigrationEvent event) {
        event.registerForMigration("atherysrpg");
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
    public void onReload(GameReloadEvent event) throws RPGCommandException {
        if (init) {
            reload(event.getCause());
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
                .id("damage_expression")
                .name("Damage Expression")
                .query(DataQuery.of("Damage_Expression"))
                .type(new TypeToken<Value<String>>() {})
                .build();

        RPGKeys.ATTRIBUTES = Key.builder()
                .id("attributes")
                .name("Attributes")
                .query(DataQuery.of("Attributes"))
                .type(new TypeToken<MapValue<AttributeType, Double>>(){})
                .build();
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
                .id("damageExpression")
                .name("DamageExpression")
                .build();

        DataRegistration.builder()
                .dataClass(AttributeMapData.class)
                .immutableClass(AttributeMapData.Immutable.class)
                .builder(new AttributeMapData.Builder())
                .id("attributes")
                .name("Attributes")
                .build();
    }

    public Logger getLogger() {
        return logger;
    }

    public AtherysRPGConfig getConfig() {
        return components.config;
    }

    public AttributesConfig getAttributesConfig() {
        return components.attributesConfig;
    }

    public SkillGraphConfig getGraphConfig() {
        return components.skillGraphConfig;
    }

    public MobsConfig getMobsConfig() {
        return components.mobsConfig;
    }

    public ArchetypesConfig getArchetypesConfig() {
        return components.archetypesConfig;
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

    public ItemFacade getItemFacade() {
        return components.itemFacade;
    }

    public Injector getRpgInjector() {
        return rpgInjector;
    }

    private static class Components {
        @Inject
        AtherysRPGConfig config;

        @Inject
        AttributesConfig attributesConfig;

        @Inject
        SkillGraphConfig skillGraphConfig;

        @Inject
        MobsConfig mobsConfig;

        @Inject
        ArchetypesConfig archetypesConfig;

        @Inject
        ItemTemplatesConfig itemTemplatesConfig;

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
        ItemFacade itemFacade;

        @Inject
        EntityListener entityListener;

        @Inject
        SkillsListener skillsListener;
    }
}
