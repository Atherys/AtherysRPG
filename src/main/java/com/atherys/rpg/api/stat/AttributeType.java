package com.atherys.rpg.api.stat;

import org.spongepowered.api.CatalogType;

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
}
