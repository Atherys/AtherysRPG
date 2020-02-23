package com.atherys.rpg.api.stat;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.Objects;

@CatalogedBy(AttributeTypes.class)
public class AttributeType implements CatalogType {

    private String id;

    private String name;

    private String shortName;

    private boolean upgradable;

    private TextColor color;

    AttributeType(String id, String shortName, String name, boolean upgradable, TextColor color) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.upgradable = upgradable;
        this.color = color;
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

    public TextColor getColor() {
        return color;
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
