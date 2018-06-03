package com.atherys.rpg.character;

import com.atherys.rpg.api.character.AbstractRPGCharacter;
import com.atherys.rpg.api.character.player.ProgressionTree;
import com.atherys.rpg.api.resource.Resource;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.MouseButtonCombo;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.UUID;

public class PlayerCharacter extends AbstractRPGCharacter {
    @Override
    public UUID getUUID() {
        return null;
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
    public Optional<Player> asLiving() {
        return Optional.empty();
    }

    @Override
    public <T extends Resource> T getResource() {
        return null;
    }

    @Override
    protected void applyMutations(ProgressionTree.Node node) {
        node.getMutators().forEach(mutator -> mutator.mutate(this));
    }
}
