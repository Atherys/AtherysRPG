package com.atherys.rpg.api.character;

import com.atherys.rpg.api.attribute.AttributeCarrier;
import com.atherys.rpg.api.character.tree.TalentTree;
import com.atherys.rpg.api.effect.ApplyableCarrier;
import com.atherys.rpg.api.resource.ResourceUser;
import com.atherys.rpg.api.skill.CastableCarrier;

import java.util.Set;

public interface RPGCharacter extends ApplyableCarrier, AttributeCarrier, ResourceUser, CastableCarrier {

    /**
     * Retrieves all nodes this RPGCharacter has selected from the given tree
     *
     * @param tree The tree whose node selections are to be returned
     * @return A set containing the nodes, or empty if no nodes have been selected from this tree
     */
    Set<TalentTree.Node> getSelectedNodesFromTree(TalentTree tree);

    /**
     * Sets the nodes selected by this RPGCharacter for the given tree.
     * All mutators stored within these nodes will be applied in a hierarchical fashion,
     * starting with those at the lowest depth, and progressing upwards.
     *
     * @param tree The tree which these nodes belong to
     * @param nodes The nodes to be set
     */
    void setSelectedNodesFromTree(TalentTree tree, Set<TalentTree.Node> nodes);

}
