package com.atherys.rpg.api.stat;

import org.spongepowered.api.text.format.TextColors;

public final class AttributeTypes {

    private static final String STRENGTH_NAME = "Strength";

    private static final String CONSTITUTION_NAME = "Constitution";

    private static final String AGILITY_NAME = "Dexterity";

    private static final String INTELLIGENCE_NAME = "Intelligence";

    private static final String WISDOM_NAME = "Wisdom";

    private static final String MAG_RESISTANCE_NAME = "Magical Resistance";

    private static final String PHY_RESISTANCE_NAME = "Physical Resistance";

    public static final AttributeType STRENGTH = new AttributeType("strength", STRENGTH_NAME, TextColors.DARK_RED);

    public static final AttributeType CONSTITUTION = new AttributeType("constitution", CONSTITUTION_NAME, TextColors.GOLD);

    public static final AttributeType AGILITY = new AttributeType("agility", AGILITY_NAME, TextColors.AQUA);

    public static final AttributeType INTELLIGENCE = new AttributeType("intelligence", INTELLIGENCE_NAME, TextColors.DARK_PURPLE);

    public static final AttributeType WISDOM = new AttributeType("wisdom", WISDOM_NAME, TextColors.DARK_GREEN);

    public static final AttributeType MAGICAL_RESISTANCE = new AttributeType("magical-resistance", MAG_RESISTANCE_NAME, TextColors.DARK_RED);

    public static final AttributeType PHYSICAL_RESISTANCE = new AttributeType("physical-resistance", PHY_RESISTANCE_NAME, TextColors.GOLD);
}
