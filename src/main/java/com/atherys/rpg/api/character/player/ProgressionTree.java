package com.atherys.rpg.api.character.player;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
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
         * @return the parent node, or null if node is parentless
         */
        Node getParent();

        /**
         * Retrieves the child-nodes
         *
         * @return The child-nodes
         */
        List<Node> getChildren();

        /**
         * Mutates the provided RPGCharacter with the data stored within this node.
         *
         * @param character The RPGCharacter to be mutated
         */
        default void mutate(RPGCharacter character) {
            getMutators().forEach(mutator -> mutator.mutate(character));
        }

        default List<Node> getPath() {
            return getPathTo(new ArrayList<>(), this);
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

    /**
     * Retrieves the path from this node to the highest level node in the tree: the root.
     *
     * @param node The node whose path is to be traced
     * @return The list of nodes leading to the root. The last node in the list should be the root node itself.
     */
    static List<Node> getPathFromRootTo(Node node) {
        return getPathTo(new ArrayList<>(), node);
    }

    static List<Node> getPathTo(List<Node> path, Node node) {
        path.add(node);
        if ( node.getParent() == null ) return path;
        return getPathTo(path, node.getParent());
    }

    /**
     * Mutates the RPGCharacter with all nodes along the path to the root. Starts with the root and
     * iterates down to the node specified.
     *
     * @param character the character to be mutated
     * @param node      The last node to be applied
     */
    static void mutateAll(RPGCharacter character, Node node) {
        List<Node> path = node.getPath();
        for (int i = path.size() - 1; i >= 0; i--) {
            character.mutate(node);
        }
    }
}
