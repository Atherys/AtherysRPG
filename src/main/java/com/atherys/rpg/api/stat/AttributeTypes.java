package com.atherys.rpg.api.stat;

import org.spongepowered.api.text.format.TextColors;

public final class AttributeTypes {

    private static final String STRENGTH_NAME = "Strength";

    private static final String CONSTITUTION_NAME = "Constitution";

    private static final String DEXTERITY_NAME = "Dexterity";

    private static final String INTELLIGENCE_NAME = "Intelligence";

    private static final String WISDOM_NAME = "Wisdom";

    private static final String MAG_RESISTANCE_NAME = "Magical Resistance";

    private static final String PHY_RESISTANCE_NAME = "Physical Resistance";

    public static final AttributeType STRENGTH = new AttributeType("str", STRENGTH_NAME, TextColors.RED);

    public static final AttributeType CONSTITUTION = new AttributeType("con", CONSTITUTION_NAME, TextColors.GOLD);

    public static final AttributeType DEXTERITY = new AttributeType("dex", DEXTERITY_NAME, TextColors.GREEN);

    public static final AttributeType INTELLIGENCE = new AttributeType("int", INTELLIGENCE_NAME, TextColors.BLUE);

    public static final AttributeType WISDOM = new AttributeType("wis", WISDOM_NAME, TextColors.LIGHT_PURPLE);

    public static final AttributeType MAGICAL_RESISTANCE = new AttributeType("magicres", MAG_RESISTANCE_NAME, TextColors.DARK_RED);

    public static final AttributeType PHYSICAL_RESISTANCE = new AttributeType("physres", PHY_RESISTANCE_NAME, TextColors.GOLD);
}
