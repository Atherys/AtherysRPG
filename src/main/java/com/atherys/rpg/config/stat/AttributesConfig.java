package com.atherys.rpg.config.stat;

import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class AttributesConfig extends PluginConfig {

    @Setting("attribute-types")
    public List<AttributeTypeConfig> ATTRIBUTE_TYPES = new ArrayList<>();
    {
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:strength",
                "str",
                "Strength",
                "",
                true,
                false,
                TextColors.RED,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:constitution",
                "con",
                "Constitution",
                "",
                true,
                false,
                TextColors.YELLOW,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:dexterity",
                "dex",
                "Dexterity",
                "",
                true,
                false,
                TextColors.GREEN,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:intelligence",
                "int",
                "Intelligence",
                "",
                true,
                false,
                TextColors.BLUE,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:wisdom",
                "wis",
                "Wisdom",
                "",
                true,
                false,
                TextColors.LIGHT_PURPLE,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:magical_resistance",
                "magicres",
                "Magical Resistance",
                "",
                false,
                false,
                TextColors.AQUA,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:physical_resistance",
                "physres",
                "Physical Resistance",
                "",
                false,
                false,
                TextColors.GOLD,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:base_armor",
                "armor",
                "Base Armor",
                "",
                false,
                true,
                TextColors.DARK_AQUA,
                1.0d
        ));
        ATTRIBUTE_TYPES.add(new AttributeTypeConfig(
                "atherys:base_damage",
                "dmg",
                "Base Damage",
                "",
                false,
                true,
                TextColors.DARK_RED,
                1.0d
        ));
    }

    public AttributesConfig() throws IOException {
        super("config/atherysrpg", "attributes.conf");
    }
}
