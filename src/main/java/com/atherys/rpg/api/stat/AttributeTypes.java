package com.atherys.rpg.api.stat;

import org.spongepowered.api.text.format.TextColors;

public final class AttributeTypes {

    private static final String STRENGTH_NAME = "Strength";

    private static final String CONSTITUTION_NAME = "Constitution";

    private static final String DEFENSE_NAME = "Endurance";

    private static final String AGILITY_NAME = "Dexterity";

    private static final String INTELLIGENCE_NAME = "Intelligence";

    private static final String CHARISMA_NAME = "Evasion";

    private static final String WISDOM_NAME = "Wisdom";

    private static final String WILLPOWER_NAME = "Willpower";

    private static final String PERCEPTION_NAME = "Perception";

    private static final String LUCK_NAME = "Luck";

    public static final AttributeType STRENGTH = new AttributeType("strength", STRENGTH_NAME, TextColors.DARK_RED);

    public static final AttributeType CONSTITUTION = new AttributeType("constitution", CONSTITUTION_NAME, TextColors.GOLD);

    public static final AttributeType DEFENSE = new AttributeType("defense", DEFENSE_NAME, TextColors.DARK_AQUA);

    public static final AttributeType AGILITY = new AttributeType("agility", AGILITY_NAME, TextColors.AQUA);

    public static final AttributeType INTELLIGENCE = new AttributeType("intelligence", INTELLIGENCE_NAME, TextColors.DARK_PURPLE);

    public static final AttributeType CHARISMA = new AttributeType("charisma", CHARISMA_NAME, TextColors.LIGHT_PURPLE);

    public static final AttributeType WISDOM = new AttributeType("wisdom", WISDOM_NAME, TextColors.DARK_GREEN);

    public static final AttributeType WILLPOWER = new AttributeType("willpower", WILLPOWER_NAME, TextColors.RED);

    public static final AttributeType PERCEPTION = new AttributeType("perception", PERCEPTION_NAME, TextColors.BLUE);

    public static final AttributeType LUCK = new AttributeType("luck", LUCK_NAME, TextColors.YELLOW);

}
