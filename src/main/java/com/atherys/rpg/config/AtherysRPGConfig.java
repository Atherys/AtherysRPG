package com.atherys.rpg.config;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.io.IOException;
import java.util.*;

@Singleton
public class AtherysRPGConfig extends PluginConfig {

    @Setting("item-damage-types")
    public Map<ItemType, String> ITEM_DAMAGE_TYPES = new HashMap<>();

    @Setting("offhand-item-types")
    public Set<ItemType> OFFHAND_ITEMS = new HashSet<>();

    @Setting("mainhand-item-types")
    public Set<ItemType> MAINHAND_ITEMS = new HashSet<>();

    @Setting("projectile-damage-types")
    public Map<EntityType, String> PROJECTILE_DAMAGE_TYPES = new HashMap<>();

    @Setting("physical-damage-mitigation-calculation")
    public String PHYSICAL_DAMAGE_MITIGATION_CALCULATION = "1.33 * SOURCE_CON";

    @Setting("magical-damage-mitigation-calculation")
    public String MAGICAL_DAMAGE_MITIGATION_CALCULATION = "1.33 * SOURCE_INT";

    @Setting("damage-production-calculations")
    public Map<String, String> DAMAGE_CALCULATIONS = new HashMap<>();

    @Setting("environmental-damage-calculations")
    public Map<DamageType, String> ENVIRONMENTAL_CALCULATIONS = new HashMap<>();

    @Setting("default-melee-damage-type")
    public String DEFAULT_MELEE_TYPE = "unarmed";

    @Setting("default-ranged-damage-type")
    public String DEFAULT_RANGED_TYPE = "ranged";

    @Setting("health-regen-calculation")
    public String HEALTH_REGEN_CALCULATION = "1.33 * SOURCE_CON";

    @Setting("health-regen-duration-in-ticks")
    public long HEALTH_REGEN_DURATION_TICKS = 1;

    @Setting("resource-regen-calculation")
    public String RESOURCE_REGEN_CALCULATION = "1.33 * SOURCE_INT";

    @Setting("resource-limit-calculation")
    public String RESOURCE_LIMIT_CALCULATION = "100.0 + SOURCE_INT * 1.5";

    @Setting("health-limit-calculation")
    public String HEALTH_LIMIT_CALCULATION = "100.0 + SOURCE_CON * 1.5";

    @Setting("health-scaling")
    public double HEALTH_SCALING = 20d;

    @Setting("movement-speed-calculation")
    public String MOVEMENT_SPEED_CALCULATION = "0.1";

    @Setting("attribute-upgrade-cost")
    public String ATTRIBUTE_UPGRADE_COST = "100.0";

    @Setting("experience-max")
    public double EXPERIENCE_MAX = 100_000.0;

    @Setting("experience-min")
    public double EXPERIENCE_MIN = 0.0;

    @Setting("experience-start")
    public double EXPERIENCE_START = 0.0;

    @Setting("attribute-max")
    public double ATTRIBUTE_MAX = 99.0;

    @Setting("attribute-min")
    public double ATTRIBUTE_MIN = 0.0;

    @Setting("experience-spending-limit")
    public double EXPERIENCE_SPENDING_LIMIT = 100_000.0;

    @Setting("attribute-spending-limit")
    public double ATTRIBUTE_SPENDING_LIMIT = 100_000.0;

    @Setting("skill-spending-limit")
    public double SKILL_SPENDING_LIMIT = 100_000.0;

    @Setting("display-root-skill")
    public boolean DISPLAY_ROOT_SKILL = true;

    @Setting("skill-message-distance")
    public double SKILL_MESSAGE_DISTANCE = 25;

    @Setting("max-reward-distance")
    public double MAX_REWARD_DISTANCE = 30.0d;

    @Setting("players-keep-inventory-on-pvp")
    public boolean PLAYERS_KEEP_INVENTORY_ON_PVP = false;

    {
        // Wood
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_HOE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_SHOVEL, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_PICKAXE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_AXE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_SWORD, "blunt");

        // Stone
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_HOE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_SHOVEL, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_PICKAXE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_AXE, "slash");
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_SWORD, "slash");

        // Iron
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_HOE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_SHOVEL, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_PICKAXE, "stab");
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_AXE, "slash");
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_SWORD, "stab");

        // Gold
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_HOE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_SHOVEL, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_PICKAXE, "stab");
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_AXE, "slash");
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_SWORD, "stab");

        // Diamond
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_HOE, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_SHOVEL, "blunt");
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_PICKAXE, "stab");
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_AXE, "slash");
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_SWORD, "stab");

        // Hand
        ITEM_DAMAGE_TYPES.put(ItemTypes.NONE, "unarmed");
    }

    {
        // Bow
        PROJECTILE_DAMAGE_TYPES.put(EntityTypes.TIPPED_ARROW, "ranged");
    }

    {
        DAMAGE_CALCULATIONS.put("blunt", "CLAMP(SOURCE_CON, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put("stab", "CLAMP(SOURCE_STR, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put("slash", "CLAMP(SOURCE_STR, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put("unarmed", "CLAMP(SOURCE_INT, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put("ranged", "CLAMP(SOURCE_DEX, 1.0, 15.0)");
    }

    protected AtherysRPGConfig() throws IOException {
        super("config/atherysrpg", "config.conf");
    }
}
