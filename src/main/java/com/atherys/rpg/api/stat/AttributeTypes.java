package com.atherys.rpg.api.stat;

import org.spongepowered.api.text.format.TextColors;

public final class AttributeTypes {

    public static final AttributeType STRENGTH = new AttributeType("str", "Strength", TextColors.RED);

    public static final AttributeType CONSTITUTION = new AttributeType("con", "Constitution", TextColors.GOLD);

    public static final AttributeType DEXTERITY = new AttributeType("dex", "Dexterity", TextColors.GREEN);

    public static final AttributeType INTELLIGENCE = new AttributeType("int", "Intelligence", TextColors.BLUE);

    public static final AttributeType WISDOM = new AttributeType("wis", "Wisdom", TextColors.LIGHT_PURPLE);

    public static final AttributeType MAGICAL_RESISTANCE = new AttributeType("magicres", "Magical Resistance", TextColors.DARK_RED);

    public static final AttributeType PHYSICAL_RESISTANCE = new AttributeType("physres", "Physical Resistance", TextColors.GOLD);
}
