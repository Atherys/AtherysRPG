package com.atherys.rpg.character;

import com.atherys.rpg.api.character.AbstractRPGCharacter;
import com.atherys.rpg.api.resource.Resource;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.MouseButtonCombo;
import org.spongepowered.api.entity.living.Living;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class PlayerCharacter extends AbstractRPGCharacter {
    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public Collection<Castable> getCastables() {
        return null;
    }

    @Override
    public Optional<? extends Castable> getCastableById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<? extends Castable> getCastableByCombo(MouseButtonCombo combo) {
        return Optional.empty();
    }

    @Override
    public Optional<MouseButtonCombo> getCurrentCombo() {
        return Optional.empty();
    }

    @Override
    public void cast(Castable castable) {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Optional<? extends Living> asLiving() {
        return Optional.empty();
    }

    @Override
    public <T extends Resource> T getResource() {
        return null;
    }
}
