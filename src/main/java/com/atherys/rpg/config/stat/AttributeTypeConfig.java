package com.atherys.rpg.config.stat;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

@ConfigSerializable
public class AttributeTypeConfig {

    @Setting("id")
    private String id = "NoId";

    @Setting("name")
    private String name = "NoNameAttribute";

    @Setting("short-name")
    private String shortName = "NoShortName";

    @Setting("description")
    private String description = "";

    @Setting("upgradable")
    private boolean upgradable = false;

    @Setting("color")
    private TextColor color = TextColors.RESET;

    @Setting("default-value")
    private double defaultValue = 0.0d;

    public AttributeTypeConfig() {
    }

    public AttributeTypeConfig(String id, String shortName, String name, String description, boolean upgradable, TextColor color, double defaultValue) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.upgradable = upgradable;
        this.color = color;
        this.defaultValue = defaultValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isUpgradable() {
        return upgradable;
    }

    public void setUpgradable(boolean upgradable) {
        this.upgradable = upgradable;
    }

    public TextColor getColor() {
        return color;
    }

    public void setColor(TextColor color) {
        this.color = color;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
