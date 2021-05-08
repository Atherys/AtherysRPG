package com.atherys.rpg.api.stat;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.text.format.TextColor;

import java.util.Objects;

public class AttributeType implements CatalogType {

    private final String id;

    private final String name;

    private final String shortName;

    private final boolean upgradable;

    private final boolean hidden;

    private final TextColor color;

    private final String description;

    private final double defaultValue;

    private final boolean resetOnLogin;

    private final String display;

    AttributeType(String id, String shortName, String name, String description, boolean upgradable, boolean hidden,
                  TextColor color, Double defaultValue, boolean resetOnLogin, String display) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.upgradable = upgradable;
        this.hidden = hidden;
        this.description = description;
        this.color = color;
        this.defaultValue = defaultValue;
        this.resetOnLogin = resetOnLogin;
        this.display = display;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    public String getDescription() {
        return description;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public boolean isResetOnLogin() {
        return resetOnLogin;
    }

    public String getDisplay() {
        return this.display.isEmpty() ? getName() + ": " + "%value%" : this.display;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeType that = (AttributeType) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return id;
    }
}
