package com.haedhutner.graphs;

import com.atherys.rpg.api.util.Graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GraphTest {

    public static final String NODE_A = "A";
    public static final String NODE_B = "B";
    public static final String NODE_C = "C";
    public static final String NODE_D = "D";
    public static final String NODE_E = "E";
    public static final String NODE_F = "F";

    public Graph<String> graph = new Graph<>(NODE_A);

    public GraphTest() {
        graph.add(NODE_A, NODE_B, Graph.LinkType.UNIDIRECTIONAL);
        graph.add(NODE_A, NODE_C, Graph.LinkType.UNIDIRECTIONAL);
        graph.add(NODE_B, NODE_C, Graph.LinkType.BIDIRECTIONAL);
        graph.add(NODE_B, NODE_D, Graph.LinkType.UNIDIRECTIONAL);
        graph.add(NODE_C, NODE_E, Graph.LinkType.UNIDIRECTIONAL);
        graph.add(NODE_C, NODE_F, Graph.LinkType.BIDIRECTIONAL);
        graph.add(NODE_F, NODE_D, Graph.LinkType.BIDIRECTIONAL);
    }
}
