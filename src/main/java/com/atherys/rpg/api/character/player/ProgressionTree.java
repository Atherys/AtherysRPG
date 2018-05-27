package com.atherys.rpg.api.character.player;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.character.tree.PlayerProgressionTree;
import org.spongepowered.api.CatalogType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A tree representing progression paths a player can take.
 * This tree implements inheritance, meaning child nodes will inherit missing properties from their parents.
 *
 * @param <T> The Node class
 */
public interface ProgressionTree<T extends ProgressionTree.Node> extends CatalogType {

    /**
     * A node will mutate a Player RPGCharacter when they have reached it.
     */
    interface Node extends CatalogType {

        /**
         * Retrieves all mutators of this node
         *
         * @return The colleciton of mutator objects
         */
        Collection<Mutator> getMutators();

        /**
         * Retrieves the parent of this node
         *
         * @return the parent node
         */
        Node getParent();

        /**
         * Retrieves the child-nodes
         *
         * @return The child-nodes
         */
        List<PlayerProgressionTree.Node> getChildren();

        /**
         * Mutates the provided RPGCharacter with the data stored within this node.
         *
         * @param character The RPGCharacter to be mutated
         */
        default void mutate(RPGCharacter character) {
            getMutators().forEach(mutator -> mutator.mutate(character));
        }

        default Collection<Node> getPath() {
            List<Node> path = new ArrayList<>();
            path.add(this);

            if ( getParent() == null ) return path;
            else path.addAll(getParent().getPath());

            return path;
        }

    }

    /**
     * Gets the root of this ProgressionTree
     *
     * @return
     */
    T getRoot();

    /**
     * Retrieves a Node by its UUID
     *
     * @param id
     * @return
     */
    Optional<T> getNodeById(String id);

}
