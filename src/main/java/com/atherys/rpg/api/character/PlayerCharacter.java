package com.atherys.rpg.api.character;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.MouseButtonCombo;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class PlayerCharacter extends AbstractRPGCharacter {

    protected PlayerCharacter() {
    }

    @Override
    public CastResult cast(Castable castable, String... args) {
        return null;
    }

    Optional<? extends Castable> getCastableByCombo(MouseButtonCombo combo) {
        return null;
    }

    Optional<MouseButtonCombo> getCurrentCombo() {
        return null;
    }

    @Override
    public Optional<? extends Living> asLiving() {
        return Optional.empty();
    }

    @Override
    @Nonnull
    public UUID getUniqueId() {
        return null;
    }
}
