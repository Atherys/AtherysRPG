package com.atherys.rpg.model.character.tree;

import com.atherys.core.views.View;
import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.tree.TalentTree;
import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlayerTalentTree implements TalentTree {

    public static class Node implements TalentTree.Node {

        @Expose
        private String id;

        @Expose
        private String name;

        @Expose
        private Set<Mutator> mutators = new HashSet<>();

        @Expose
        private Node parent;

        @Expose
        private Set<Node> children = new HashSet<>();

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Set<Mutator> getMutators() {
            return mutators;
        }

        @Override
        public Node getParent() {
            return parent;
        }

        @Override
        public Set<? extends TalentTree.Node> getChildren() {
            return children;
        }

        @Override
        public int getDepth() {
            return parent == null ? 0 : parent.getDepth() + 1;
        }
    }

    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private Node root;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TalentTree.Node getRoot() {
        return root;
    }

    @Override
    public Optional<TalentTree.Node> getNodeById(String id) {
        return getNodeById(root, id);
    }

    private Optional<TalentTree.Node> getNodeById(TalentTree.Node startingPoint, String id) {
        if ( startingPoint.getId().equals(id) ) return Optional.of(startingPoint);
        else {
            for ( TalentTree.Node node : startingPoint.getChildren() ) {
                Optional<TalentTree.Node> nodeById = getNodeById(node, id);
                if ( nodeById.isPresent() ) return nodeById;
            }
            return Optional.empty();
        }
    }

    @Override
    public View createView() {
        return null;
    }

}
