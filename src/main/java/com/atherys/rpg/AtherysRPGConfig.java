package com.atherys.rpg;

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
    public Map<ItemType, AtherysDamageType> ITEM_DAMAGE_TYPES = new HashMap<>();
    {
        // Wood
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_HOE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_SHOVEL, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_PICKAXE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_AXE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.WOODEN_SWORD, AtherysDamageTypes.BLUNT);

        // Stone
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_HOE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_SHOVEL, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_PICKAXE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_AXE, AtherysDamageTypes.SLASH);
        ITEM_DAMAGE_TYPES.put(ItemTypes.STONE_SWORD, AtherysDamageTypes.SLASH);

        // Iron
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_HOE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_SHOVEL, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_PICKAXE, AtherysDamageTypes.STAB);
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_AXE, AtherysDamageTypes.SLASH);
        ITEM_DAMAGE_TYPES.put(ItemTypes.IRON_SWORD, AtherysDamageTypes.STAB);

        // Gold
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_HOE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_SHOVEL, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_PICKAXE, AtherysDamageTypes.STAB);
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_AXE, AtherysDamageTypes.SLASH);
        ITEM_DAMAGE_TYPES.put(ItemTypes.GOLDEN_SWORD, AtherysDamageTypes.STAB);

        // Diamond
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_HOE, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_SHOVEL, AtherysDamageTypes.BLUNT);
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_PICKAXE, AtherysDamageTypes.STAB);
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_AXE, AtherysDamageTypes.SLASH);
        ITEM_DAMAGE_TYPES.put(ItemTypes.DIAMOND_SWORD, AtherysDamageTypes.STAB);

        // Hand
        ITEM_DAMAGE_TYPES.put(ItemTypes.NONE, AtherysDamageTypes.UNARMED);
    }

    @Setting("projectile-damage-types")
    public Map<EntityType, AtherysDamageType> PROJECTILE_DAMAGE_TYPES = new HashMap<>();
    {
        // Bow
        PROJECTILE_DAMAGE_TYPES.put(EntityTypes.TIPPED_ARROW, AtherysDamageTypes.PIERCE);
    }

    @Setting("damage-calculations")
    public Map<AtherysDamageType, String> DAMAGE_CALCULATIONS = new HashMap<>();
    {
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.BLUNT, "IF(SOURCE_STRENGTH <= TARGET_DEFENSE, 1.0, SOURCE_CONSTITUTION - TARGET_DEFENSE)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.STAB, "IF(SOURCE_STRENGTH <= TARGET_DEFENSE, 1.0, SOURCE_STRENGTH - TARGET_DEFENSE)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.SLASH, "IF(SOURCE_STRENGTH <= TARGET_DEFENSE, 1.0, SOURCE_STRENGTH - TARGET_DEFENSE)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.UNARMED, "IF(SOURCE_STRENGTH <= TARGET_DEFENSE, 1.0, SOURCE_WILLPOWER - TARGET_DEFENSE)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.PIERCE, "IF(SOURCE_STRENGTH <= TARGET_DEFENSE, 1.0, SOURCE_AGILITY - TARGET_DEFENSE)");
        DAMAGE_CALCULATIONS.put(AtherysDamageTypes.BALLISTIC, "IF(SOURCE_STRENGTH <= TARGET_DEFENSE, 1.0, SOURCE_AGILITY - TARGET_DEFENSE)");
    }

    @Setting("health-regen-calculation")
    public String HEALTH_REGEN_CALCULATION = "1.33 * TARGET_CONSTITUTION";

    @Setting("resource-regen-calculation")
    public String RESOURCE_REGEN_CALCULATION = "1.33 * TARGET_WISDOM";

    @Setting("default-attributes")
    public Map<AttributeType, Double> DEFAULT_ATTRIBUTES = new HashMap<>();
    {
        DEFAULT_ATTRIBUTES.put(AttributeTypes.STRENGTH, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.CONSTITUTION, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.DEFENSE, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.AGILITY, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.INTELLIGENCE, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.CHARISMA, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.WISDOM, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.WILLPOWER, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.PERCEPTION, 1.0d);
        DEFAULT_ATTRIBUTES.put(AttributeTypes.LUCK, 1.0d);
    }

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

    protected AtherysRPGConfig() throws IOException {
        super("config/atherysrpg", "config.conf");
    }
}
