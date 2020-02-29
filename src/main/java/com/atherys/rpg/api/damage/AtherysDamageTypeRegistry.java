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
@Deprecated
public final class AtherysDamageTypeRegistry implements CatalogRegistryModule<AtherysDamageType> {

    protected Map<String, AtherysDamageType> damageTypes = new HashMap<>();

    public AtherysDamageTypeRegistry() {
        damageTypes.put(AtherysDamageTypes.BLUNT.getId(), AtherysDamageTypes.BLUNT);
        damageTypes.put(AtherysDamageTypes.STAB.getId(), AtherysDamageTypes.STAB);
        damageTypes.put(AtherysDamageTypes.SLASH.getId(), AtherysDamageTypes.SLASH);
        damageTypes.put(AtherysDamageTypes.UNARMED.getId(), AtherysDamageTypes.UNARMED);

        damageTypes.put(AtherysDamageTypes.PIERCE.getId(), AtherysDamageTypes.PIERCE);
        damageTypes.put(AtherysDamageTypes.BALLISTIC.getId(), AtherysDamageTypes.BALLISTIC);

        damageTypes.put(AtherysDamageTypes.FIRE.getId(), AtherysDamageTypes.FIRE);
        damageTypes.put(AtherysDamageTypes.ICE.getId(), AtherysDamageTypes.ICE);
        damageTypes.put(AtherysDamageTypes.ARCANE.getId(), AtherysDamageTypes.ARCANE);
        damageTypes.put(AtherysDamageTypes.SHOCK.getId(), AtherysDamageTypes.SHOCK);
        damageTypes.put(AtherysDamageTypes.NATURE.getId(), AtherysDamageTypes.NATURE);
        damageTypes.put(AtherysDamageTypes.MENTAL.getId(), AtherysDamageTypes.MENTAL);
        damageTypes.put(AtherysDamageTypes.RADIANT.getId(), AtherysDamageTypes.RADIANT);
        damageTypes.put(AtherysDamageTypes.NECROTIC.getId(), AtherysDamageTypes.NECROTIC);
        damageTypes.put(AtherysDamageTypes.BLOOD.getId(), AtherysDamageTypes.BLOOD);
    }

    @Override
    public Optional<AtherysDamageType> getById(@Nonnull String id) {
        return Optional.ofNullable(damageTypes.get(id));
    }

    @Override
    public Collection<AtherysDamageType> getAll() {
        return damageTypes.values();
    }
}
