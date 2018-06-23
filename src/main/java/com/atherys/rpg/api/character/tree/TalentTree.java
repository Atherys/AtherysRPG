package com.atherys.rpg.api.character.tree;

import com.atherys.core.views.Viewable;
import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.util.SimpleIdentifiable;

import java.util.*;

/**
 * A tree representing progression paths a tree can take.
 * This tree implements inheritance, meaning child nodes will inherit missing properties from their parents.
 */
public interface TalentTree extends SimpleIdentifiable, Viewable {

    /**
     * A node will mutate an RPGCharacter when they have reached it.
     */
    interface Node extends SimpleIdentifiable {

        /**
         * Retrieves all mutators of this node
         *
         * @return The colleciton of mutator objects
         */
        Set<Mutator> getMutators();

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
        Set<? extends Node> getChildren();

        /**
         * Mutates the provided RPGCharacter with the data stored within this node.
         *
         * @param character The RPGCharacter to be mutated
         */
        default void mutate(RPGCharacter character) {
            getMutators().forEach(mutator -> mutator.mutate(character));
        }

        /**
         * Retrieve the depth within the tree this node sits
         *
         * @return The depth, where the tree root node is at depth 0, its children at 1 and so forth
         */
        int getDepth();

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
     * @return the root node
     */
    Node getRoot();

    /**
     * Retrieves a Node by its id
     *
     * @param id the id to search for
     * @return optional containing the node with this id
     */
    Optional<Node> getNodeById(String id);

}
