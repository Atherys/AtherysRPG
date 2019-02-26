package com.atherys.rpg.api.damage;

import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.util.annotation.CatalogedBy;

/**
 * A CatalogType extending {@link DamageType} for adding custom A'therys-related damage types.
 */
@CatalogedBy(AtherysDamageTypes.class)
public class AtherysDamageType implements DamageType {

    private final String id;
    private final String name;

    private final DamageType primitive;

    AtherysDamageType(String id, String name, DamageType primitive) {
        this.id = id;
        this.name = name;
        this.primitive = primitive;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public DamageType getPrimitive() {
        return primitive;
    }

}
