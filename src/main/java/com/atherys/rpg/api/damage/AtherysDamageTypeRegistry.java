package com.atherys.rpg.api.damage;

import org.spongepowered.api.registry.CatalogRegistryModule;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@link CatalogRegistryModule} for {@link AtherysDamageType}
 */
public final class AtherysDamageTypeRegistry implements CatalogRegistryModule<AtherysDamageType> {

    private static final AtherysDamageTypeRegistry instance = new AtherysDamageTypeRegistry();

    protected Map<String, AtherysDamageType> flags = new HashMap<>();

    public static AtherysDamageTypeRegistry getInstance() {
        return instance;
    }

    @Override
    public Optional<AtherysDamageType> getById(@Nonnull String id) {
        return Optional.ofNullable(flags.get(id));
    }

    @Override
    public Collection<AtherysDamageType> getAll() {
        return flags.values();
    }
}
