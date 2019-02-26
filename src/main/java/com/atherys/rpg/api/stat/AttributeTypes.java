package com.atherys.rpg.api.stat;

import org.spongepowered.api.text.format.TextColors;

public final class AttributeTypes {

    public static final AttributeType STRENGTH = new AttributeType("strength", "Strength", TextColors.DARK_RED);

    public static final AttributeType CONSTITUTION = new AttributeType("constitution", "Constitution", TextColors.GOLD);

    public static final AttributeType DEFENSE = new AttributeType("defense", "Defense", TextColors.DARK_AQUA);

    public static final AttributeType AGILITY = new AttributeType("agility", "Agility", TextColors.AQUA);

    public static final AttributeType INTELLIGENCE = new AttributeType("intelligence", "Intelligence", TextColors.DARK_PURPLE);

    public static final AttributeType CHARISMA = new AttributeType("charisma", "Charisma", TextColors.LIGHT_PURPLE);

    public static final AttributeType WISDOM = new AttributeType("wisdom", "Wisdom", TextColors.DARK_GREEN);

    public static final AttributeType WILLPOWER = new AttributeType("willpower", "Willpower", TextColors.RED);

    public static final AttributeType PERCEPTION = new AttributeType("perception", "Perception", TextColors.BLUE);

    public static final AttributeType LUCK = new AttributeType("luck", "Luck", TextColors.YELLOW);

}
