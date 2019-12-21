package com.atherys.rpg.api.stat;

import org.spongepowered.api.text.format.TextColors;

public final class AttributeTypes {

    public static final AttributeType STRENGTH = new AttributeType("atherys:strength", "str", "Strength", TextColors.RED);

    public static final AttributeType CONSTITUTION = new AttributeType("atherys:constitution", "con", "Constitution", TextColors.GOLD);

    public static final AttributeType DEXTERITY = new AttributeType("atherys:dexterity", "dex", "Dexterity", TextColors.GREEN);

    public static final AttributeType INTELLIGENCE = new AttributeType("atherys:intelligence", "int", "Intelligence", TextColors.BLUE);

    public static final AttributeType WISDOM = new AttributeType("atherys:wisdom", "wis", "Wisdom", TextColors.LIGHT_PURPLE);

    public static final AttributeType MAGICAL_RESISTANCE = new AttributeType("atherys:magic_resistance", "magicres", "Magical Resistance", TextColors.DARK_RED);

    public static final AttributeType PHYSICAL_RESISTANCE = new AttributeType("atherys:physical_resistance", "physres", "Physical Resistance", TextColors.GOLD);

    public static final AttributeType BASE_ARMOR = new AttributeType("atherys:base_armor", "armor", "Base Armor", TextColors.DARK_AQUA);

    public static final AttributeType BASE_DAMAGE = new AttributeType("atherys:base_damage", "dmg", "Base Damage", TextColors.DARK_RED);
}
