package com.atherys.rpg.api.util;

import spark.utils.Assert;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Graph<T> {

    public enum LinkType {
        UNIDIRECTIONAL,
        BIDIRECTIONAL
    }

    public static class Node<T> {

        private T data;

        Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
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

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    public static class Link<T> {

        private Node<T> parent;

        private Node<T> child;

        private double weight;

        private LinkType type;

        Link(Node<T> parent, Node<T> child, double weight, LinkType type) {
            this.parent = parent;
            this.child = child;
            this.weight = weight;
            this.type = type;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getChild() {
            return child;
        }

        public void setChild(Node<T> child) {
            this.child = child;
        }

        public LinkType getType() {
            return type;
        }

        public void setType(LinkType type) {
            this.type = type;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link<?> link = (Link<?>) o;
            return parent.equals(link.parent) &&
                    child.equals(link.child) &&
                    type == link.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(parent, child, type);
        }

        @Override
        public String toString() {
            return "Link{" +
                    "parent=" + parent +
                    ", child=" + child +
                    ", type=" + type +
                    '}';
        }
    }

    private Node<T> lastAdded;

    private Set<Node<T>> nodes = new HashSet<>();

    private Set<Link<T>> links = new HashSet<>();

    public Graph(T root) {
        Node<T> firstNode = new Node<>(root);
        nodes.add(firstNode);
        links.add(new Link<>(firstNode, firstNode, -1.0, LinkType.UNIDIRECTIONAL));
        lastAdded = firstNode;
    }

    public Set<Node<T>> getNodes() {
        return nodes;
    }

    public Set<Link<T>> getLinks() {
        return links;
    }

    public void forEach(Consumer<T> consumer) {
        nodes.forEach(node -> consumer.accept(node.getData()));
    }

    /**
     * Check whether the graph contains the object
     *
     * @param object The object to search for
     * @return Whether it is contained within the graph or not
     */
    public boolean contains(T object) {
        return nodes.contains(new Node<>(object));
    }

    /**
     * Adds new nodes to the graph, while also defining their relationship within it.
     * <p>
     * If both objects already exist within the graph, and they have a relationship between them, it will be altered.
     *
     * @param parent   The parent object
     *                 ( if this does not already exist in the graph, it will be linked bidirectionally with a default weight of 0.0 to the last added node before it. )
     * @param object   The child object
     *                 ( if this already exists in the graph, a new link will be created between it and the parent )
     * @param weight   The weight of the new relationship
     *                 ( if a relationship already exists, it will be modified )
     * @param linkType The type of link to be created between the objects
     *                 ( if a relationship already exists, it will be modified )
     */
    public void add(T parent, T object, double weight, LinkType linkType) {
        Assert.notNull(parent, "Parent cannot be null.");
        Assert.notNull(object, "Object cannot be null.");
        Assert.notNull(linkType, "Link type cannot be null.");

        Node<T> parentNode = new Node<>(parent);
        Node<T> childNode = new Node<>(object);

        // If the parent node does not exist, create it and link it bidirectionally with the last node added to the graph
        if (!nodes.contains(parentNode)) {
            nodes.add(parentNode);
            links.add(new Link<>(lastAdded, parentNode, 0.0, LinkType.BIDIRECTIONAL));
        }

        // If a link already exists between the objects, modify it
        Link<T> existingLink = getLink(parent, object);
        if (existingLink != null) {
            existingLink.setWeight(weight);
            existingLink.setType(linkType);
            return;
        }

        // If no existing link could be found, add the child node and create a new link between it and the parent
        nodes.add(childNode);
        links.add(new Link<>(parentNode, childNode, weight, linkType));
    }

    /**
     * Add a new parent-object pair, with a default weight of 0.0.
     * This method follows the same rules as {@link #add(Object, Object, double, LinkType)}.
     *
     * @param parent   The parent object
     * @param object   The child object
     * @param linkType The type of link between them
     */
    public void add(T parent, T object, LinkType linkType) {
        add(parent, object, 0.0, linkType);
    }

    /**
     * Add a new bidirectional parent-object pair, with a default weight of 0.0
     * This method follows the same rules as {@link #add(Object, Object, double, LinkType)}.
     *
     * @param parent the parent
     * @param object the child
     */
    public void add(T parent, T object) {
        add(parent, object, LinkType.BIDIRECTIONAL);
    }

    /**
     * Add a new link between the last added object and the one provided.
     * This method follows the same rules as {@link #add(Object, Object, double, LinkType)}.
     *
     * @param object The child object to attach to the last one added
     * @param weight the weight of the new link
     * @param linkType the type of the link
     */
    public void add(T object, double weight, LinkType linkType) {
        add(lastAdded.getData(), object, weight, linkType);
    }

    /**
     * Add a new link between the last added object and the one provided, with a default weight of 0.0.
     * This method follows the same rules as {@link #add(Object, Object, double, LinkType)}.
     *
     * @param object the child object to link to the last one added
     * @param linkType The type of link to create
     */
    public void add(T object, LinkType linkType) {
        add(lastAdded.getData(), 0.0, linkType);
    }

    /**
     * Add a new bidirectional link between the last added object and the one provided, with a default weight of 0.0.
     * This method follows the same rules as {@link #add(Object, Object, double, LinkType)}.
     *
     * @param object the child object to be added
     */
    public void add(T object) {
        add(lastAdded.getData(), object);
    }

    /**
     * Retrieve all links within the graph where the provided object is either a child or parent.
     *
     * @param object The criteria parent/child object
     * @return A set of links, or empty if none could be found
     */
    public Set<Link<T>> getLinks(T object) {
        Assert.notNull(object, "Object cannot be null.");
        return links.stream().filter(link -> link.getParent().getData().equals(object) || link.getChild().getData().equals(object)).collect(Collectors.toSet());
    }

    /**
     * Get the relationship between 2 objects ( if any ) within the graph.
     *
     * @param object1 The first object
     * @param object2 The second object
     * @return The link between them, or null if none exists
     */
    public Link<T> getLink(T object1, T object2) {
        Assert.isTrue(object1 != null && object2 != null, "Neither object can be null.");
        return links.stream().filter(link -> {
            T linkParent = link.getParent().getData();
            T linkChild = link.getChild().getData();
            return (linkParent.equals(object1) && linkChild.equals(object2)) || (linkParent.equals(object2) && linkChild.equals(object1));
        }).findAny().orElse(null);
    }

    @Override
    public String toString() {
        return "Graph{" + links + '}';
    }
}
