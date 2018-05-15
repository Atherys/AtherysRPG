package com.atherys.rpg.api.character.player;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.skill.Castable;
import org.spongepowered.api.data.DataContainer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * A tree representing progression paths a player can take.
 * This tree implements inheritance, meaning child nodes will inherit missing properties from their parents.
 *
 * @param <T> The Node class
 */
public interface ProgressionTree<T extends ProgressionTree.Node> {

    /**
     * A node will mutate a Player RPGCharacter when they have reached it.
     */
    interface Node {

        /**
         * Every node must have a unique identifier;
         *
         * @return the UUID of this Node
         */
        UUID getUUID();

        /**
         * Provides the Castables that will be applied to an RPGCharacter upon mutation
         *
         * @return The castables
         */
        Collection<Castable> getCastables();

        /**
         * Provides the Attributes that will be applied to an RPGCharacter upon mutation
         *
         * @return The attributes
         */
        Collection<Attribute> getAttributes();

        /**
         * Upon mutation, every key-value in this DataContainer will
         * be applied to the Living entity from the RPGCharacter,
         * if applicable
         *
         * @return The data container
         */
        DataContainer getModifiers();

        /**
         * Retrieves the child-nodes
         *
         * @return The child-nodes;
         */
        Collection<Node> getChildren();

        /**
         * Mutates the provided RPGCharacter with the Castables, Attributes and DataContainer stored within this node.
         *
         * @param character The RPGCharacter to be mutated
         */
        void mutate(RPGCharacter character);


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
    Optional<T> getNodeById(UUID id);

}
