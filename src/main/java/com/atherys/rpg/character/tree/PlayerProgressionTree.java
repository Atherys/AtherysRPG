package com.atherys.rpg.character.tree;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.character.player.ProgressionTree;
import com.atherys.rpg.api.skill.Castable;
import org.spongepowered.api.data.DataContainer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class PlayerProgressionTree implements ProgressionTree<PlayerProgressionTree.Node> {

    public static class Node implements ProgressionTree.Node {

        @Override
        public UUID getUUID() {
            return null;
        }

        @Override
        public Collection<Castable> getCastables() {
            return null;
        }

        @Override
        public Collection<Attribute> getAttributes() {
            return null;
        }

        @Override
        public DataContainer getModifiers() {
            return null;
        }

        @Override
        public Collection<ProgressionTree.Node> getChildren() {
            return null;
        }

        @Override
        public void mutate(RPGCharacter character) {
        }
    }

    @Override
    public Node getRoot() {
        return null;
    }

    @Override
    public Optional<Node> getNodeById(UUID id) {
        return Optional.empty();
    }

}
