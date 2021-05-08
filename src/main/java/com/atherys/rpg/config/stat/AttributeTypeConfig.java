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

    @Setting("hidden")
    private boolean hidden = false;

    @Setting("color")
    private TextColor color = TextColors.RESET;

    @Setting("default-value")
    private double defaultValue = 0.0d;

    @Setting(value = "reset-on-login", comment = "Whether this attribute should reset on login")
    private boolean resetOnLogin = false;

    @Setting("display")
    private String display = "";

    public AttributeTypeConfig() {
    }

    public AttributeTypeConfig(String id, String shortName, String name, String description, boolean upgradable,
                               boolean hidden, TextColor color, double defaultValue, boolean resetOnLogin, String display) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.upgradable = upgradable;
        this.hidden = hidden;
        this.color = color;
        this.defaultValue = defaultValue;
        this.resetOnLogin = resetOnLogin;
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean isUpgradable() {
        return upgradable;
    }

    public boolean isHidden() {
        return hidden;
    }

    public TextColor getColor() {
        return color;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public boolean isResetOnLogin() {
        return resetOnLogin;
    }

    public String getDisplay() {
        return display;
    }
}
