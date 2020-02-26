package com.atherys.rpg.config;

import com.atherys.core.utils.PluginConfig;
import com.atherys.rpg.api.damage.AtherysDamageType;
import com.atherys.rpg.api.damage.AtherysDamageTypes;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.api.stat.AttributeTypes;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AtherysRPGConfig extends PluginConfig {

    @Setting("item-damage-types")
    public Map<ItemType, String> ITEM_DAMAGE_TYPES = new HashMap<>();

    @Setting("projectile-damage-types")
    public Map<EntityType, String> PROJECTILE_DAMAGE_TYPES = new HashMap<>();

    @Setting("physical-damage-mitigation-calculation")
    public String PHYSICAL_DAMAGE_MITIGATION_CALCULATION = "1.33 * SOURCE_CON";

    @Setting("magical-damage-mitigation-calculation")
    public String MAGICAL_DAMAGE_MITIGATION_CALCULATION = "1.33 * SOURCE_INT";

    @Setting("damage-production-calculations")
    public Map<String, String> DAMAGE_CALCULATIONS = new HashMap<>();

    @Setting("health-regen-calculation")
    public String HEALTH_REGEN_CALCULATION = "1.33 * SOURCE_CON";

    @Setting("health-regen-duration-in-ticks")
    public long HEALTH_REGEN_DURATION_TICKS = 1;

    @Setting("resource-regen-calculation")
    public String RESOURCE_REGEN_CALCULATION = "1.33 * SOURCE_INT";

    @Setting("health-limit-calculation")
    public String HEALTH_LIMIT_CALCULATION = "100.0 * SOURCE_INT";

    @Setting("default-attributes")
    public Map<AttributeType, Double> DEFAULT_ATTRIBUTES = new HashMap<>();

    @Setting("attribute-upgrade-cost")
    public double ATTRIBUTE_UPGRADE_COST = 100.0;

    @Setting("experience-max")
    public double EXPERIENCE_MAX = 100_000.0;

    @Setting("experience-min")
    public double EXPERIENCE_MIN = 0.0;

    @Setting("attribute-max")
    public double ATTRIBUTE_MAX = 99.0;

    @Setting("attribute-min")
    public double ATTRIBUTE_MIN = 0.0;

    @Setting("default-experience-spending-limit")
    public double DEFAULT_EXPERIENCE_SPENDING_LIMIT = 100_000.0;

    {
        // Wood
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_HOE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_SHOVEL, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_PICKAXE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_AXE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_SWORD, AtherysDamageTypes.BLUNT.getId());

        // Stone
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_HOE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_SHOVEL, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_PICKAXE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_AXE, AtherysDamageTypes.SLASH.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_SWORD, AtherysDamageTypes.SLASH.getId());

        // Iron
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_HOE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_SHOVEL, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_PICKAXE, AtherysDamageTypes.STAB.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_AXE, AtherysDamageTypes.SLASH.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_SWORD, AtherysDamageTypes.STAB.getId());

        // Gold
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_HOE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_SHOVEL, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_PICKAXE, AtherysDamageTypes.STAB.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_AXE, AtherysDamageTypes.SLASH.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_SWORD, AtherysDamageTypes.STAB.getId());

        // Diamond
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_HOE, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_SHOVEL, AtherysDamageTypes.BLUNT.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_PICKAXE, AtherysDamageTypes.STAB.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_AXE, AtherysDamageTypes.SLASH.getId());
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_SWORD, AtherysDamageTypes.STAB.getId());

        // Hand
        ITEM_DAMAGE_TYPES.put(ItemTypes.NONE, AtherysDamageTypes.UNARMED.getId());
    }

    {
        // Bow
        PROJECTILE_DAMAGE_TYPES.put(EntityTypes.TIPPED_ARROW, AtherysDamageTypes.PIERCE.getId());
    }

    {
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.BLUNT.getId(), "CLAMP(SOURCE_CON, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.STAB.getId(), "CLAMP(SOURCE_STR, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.SLASH.getId(), "CLAMP(SOURCE_STR, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.UNARMED.getId(), "CLAMP(SOURCE_INT, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.PIERCE.getId(), "CLAMP(SOURCE_DEX, 1.0, 15.0)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.BALLISTIC.getId(), "CLAMP(SOURCE_DEX, 1.0, 15.0)");
    }

    {
        DEFAULT_ATTRIBUTES.put(AttributeTypes.STRENGTH, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.CONSTITUTION, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.DEXTERITY, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.INTELLIGENCE, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.WISDOM, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.MAGICAL_RESISTANCE, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.PHYSICAL_RESISTANCE, 1.0d);
    }

    protected AtherysRPGConfig() throws IOException {
        super("config/atherysrpg", "config.conf");
    }
}
