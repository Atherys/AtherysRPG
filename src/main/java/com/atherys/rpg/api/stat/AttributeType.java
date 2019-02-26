package com.atherys.rpg.api.stat;

import org.spongepowered.api.CatalogType;

import java.util.Objects;

public class AttributeType implements CatalogType {

    private String id;

    private String name;

    AttributeType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
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
}
