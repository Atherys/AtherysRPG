package com.atherys.rpg.api.util;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class Graph<T> {

    public static class Node<T> {

        private T data;

        private Set<Edge<T>> links = new HashSet<>();

        Node(T data) {
            this.data = data;
        }

        public static <T> Node<T> of(T data) {
            return new Node<>(data);
        }

        public T get() {
            return data;
        }

        public Set<Edge<T>> getLinks() {
            return links;
        }

        protected boolean addLink(Node<T> target) {
            return addLink(new Edge<>(this, target));
        }

        protected boolean addLink(Node<T> target, float weight) {
            return addLink(new Edge<>(this, target, weight));
        }

        private boolean addLink(Edge<T> edge) {
            if (links.contains(edge)) return false;

            boolean result = links.add(edge);

            if (!edge.getTarget().containsLink(this)) {
                result = result && edge.getTarget().addLink(edge.inverse());
            }

            return result;
        }

        protected boolean removeLink(Node<T> target) {
            Edge<T> edge = Edge.of(this, target);

            if (!links.contains(edge)) return false;

            boolean result = links.remove(edge);

            if (target.containsLink(this)) {
                result = result && target.removeLink(this);
            }

            return result;
        }

        public boolean containsLink(Node<T> criteria) {
            return links.stream().anyMatch(edge -> edge.getTarget().equals(criteria));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(data, node.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    public static class Edge<T> {

        private Node<T> source;
        private Node<T> target;

        private float weight;

        Edge(Node<T> source, Node<T> target) {
            this.source = source;
            this.target = target;
        }

        Edge(Node<T> source, Node<T> target, float weight) {
            this(source, target);
            this.weight = weight;
        }

        public static <T> Edge<T> of(Node<T> source, Node<T> target) {
            return new Edge<>(source, target);
        }

        public static <T> Edge<T> of(Node<T> source, Node<T> target, float weight) {
            return new Edge<>(source, target, weight);
        }

        public Node<T> getSource() {
            return source;
        }

        public Node<T> getTarget() {
            return target;
        }

        public float getWeight() {
            return weight;
        }

        protected Edge<T> inverse() {
            return Edge.of(target, source, weight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return (Objects.equals(source, edge.source) &&
                    Objects.equals(target, edge.target)) ||
                    (Objects.equals(source, edge.target) &&
                            Objects.equals(target, edge.source));
        }

        @Override
        public int hashCode() {
            return Objects.hash(source) + Objects.hash(target);
        }
    }

    private Node<T> root;

    public Graph() {
    }

    public Graph(T firstElement) {
        this.root = Node.of(firstElement);
    }

    public Node<T> getRoot() {
        return root;
    }

    /**
     * Insert a new node into the graph.
     * If a root does is not already present, the inserted data will become it. Otherwise, the inserted data will
     * be linked directly to the root node.
     *
     * @param data the data to be inserted
     * @throws IllegalArgumentException if the provided data is null.
     */
    public void insert(T data) {
        insert(data, 0.0f);
    }

    /**
     * Insert a new node into the graph.
     * The data will be linked with the parent node provided. If the parent node does not exist, this will fail with
     * an {@link IllegalArgumentException}.
     *
     * @param parent the parent data
     * @param data   The new data to be inserted
     * @throws IllegalArgumentException If the data to be inserted is null, or the parent data is not present.
     */
    public void insert(T parent, T data) {
        insert(parent, data, 0.0f);
    }

    /**
     * Insert a new node into the graph with a weight on the new edge that is created.
     * If a root does is not already present, the inserted data will become it. Otherwise, the inserted data will
     * be linked directly to the root node.
     *
     * @param data   the data to be inserted
     * @param weight the weight of the edge
     * @throws IllegalArgumentException if the provided data is null.
     */
    public void insert(T data, float weight) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            root = Node.of(data);
        } else {
            insert(root, Node.of(data), weight);
        }
    }

    /**
     * Insert a new node into the graph with a weight on the newly created edge.
     * The data will be linked with the parent node provided. If the parent node does not exist, this will fail with
     * an {@link IllegalArgumentException}.
     *
     * @param parent the parent data
     * @param data   The new data to be inserted
     * @param weight the weight of the edge
     * @throws IllegalArgumentException If the data to be inserted is null, or the parent data is not present.
     */
    public void insert(T parent, T data, float weight) {
        if (data == null) {
            throw new IllegalArgumentException();
        }

        Optional<Node<T>> parentNode = findNode(parent);

        if (!parentNode.isPresent()) {
            throw new IllegalArgumentException();
        }

        insert(parentNode.get(), Node.of(data), weight);
    }

    private void insert(Node<T> parent, Node<T> node, float weight) {
        parent.addLink(node, weight);
    }

    /**
     * Remove a node form the graph with the provided data
     *
     * @param data the data to be removed
     */
    public void remove(T data) {
        findNode(data).ifPresent(node -> {
            new HashSet<>(node.getLinks()).forEach(edge -> {
                edge.getTarget().removeLink(node);
            });
        });
    }

    /**
     * Link 2 nodes with the provided data values
     *
     * @param source the source node
     * @param target the target node
     * @return true, if both the source and target node already exist in the graph and have been linked successfully.
     * False if otherwise.
     */
    public boolean link(T source, T target) {
        Optional<Node<T>> sourceNode = findNode(source);
        Optional<Node<T>> targetNode = findNode(target);

        if (sourceNode.isPresent() && targetNode.isPresent()) {
            return sourceNode.get().addLink(targetNode.get());
        }

        return false;
    }

    /**
     * Remove the link between 2 nodes with the provided data values
     *
     * @param source the source data
     * @param target the target data
     * @return true, if the nodes were unlinked successfully. False if otherwise.
     */
    public boolean unlink(T source, T target) {
        Optional<Node<T>> sourceNode = findNode(source);
        Optional<Node<T>> targetNode = findNode(target);

        if (sourceNode.isPresent() && targetNode.isPresent()) {
            return sourceNode.get().removeLink(targetNode.get());
        }

        return false;
    }

    /**
     * Check if there is an existing edge between nodes containing the specified data
     *
     * @param source the source node
     * @param target the target node
     * @return Whether they are linked or not
     */
    public boolean areLinked(T source, T target) {
        Optional<Node<T>> sourceNode = findNode(source);
        Optional<Node<T>> targetNode = findNode(target);

        if (sourceNode.isPresent() && targetNode.isPresent()) {
            return sourceNode.get().containsLink(targetNode.get());
        }

        return false;
    }

    /**
     * Find the node containing the specified data
     *
     * @param data the data to look for
     * @return An optional containing the node if found, an empty optional if not
     */
    public Optional<Node<T>> findNode(T data) {
        return Optional.ofNullable(depthFirstSearch(new HashSet<>(), root, data));
    }

    /**
     * Recursively look through the nodes, starting with the one provided, for a node meeting the specified criteria
     *
     * @param checkedNodes A hashset containing already-checked nodes
     * @param start        Where to start the search
     * @param criteria     What to look for
     * @return the node containing the data
     */
    private Node<T> depthFirstSearch(Set<Node<T>> checkedNodes, Node<T> start, T criteria) {
        if (start.get().equals(criteria)) {
            return start;
        } else {
            checkedNodes.add(start); // set starting node to checked
            Node<T> result = null;

            for (Edge<T> edge : start.getLinks()) {
                if (!checkedNodes.contains(edge.getTarget())) { // if target has not already been checked, check it
                    result = depthFirstSearch(checkedNodes, edge.getTarget(), criteria);
                    if (result != null) break;
                }
            }

            return result;
        }
    }

    /**
     * Iterate over all the nodes in this graph, without repeating
     *
     * @param consumer What to do with each node
     */
    public void forEach(Consumer<? super Node<T>> consumer) {
        forEach(new HashSet<>(), root, consumer);
    }

    private void forEach(Set<Node<T>> iteratedNodes, Node<T> start, Consumer<? super Node<T>> consumer) {
        consumer.accept(start);

        iteratedNodes.add(start);

        for (Edge<T> edge : start.getLinks()) {
            if (!iteratedNodes.contains(edge.getTarget())) {
                forEach(iteratedNodes, edge.getTarget(), consumer);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph<?> graph = (Graph<?>) o;
        return Objects.equals(root, graph.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}
