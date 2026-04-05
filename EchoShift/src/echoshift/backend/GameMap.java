// GameMap.java
package echoshift.backend;

import graph.GraphADT;
import graph.GraphNode;
import graph.GraphEdge;
import graph.UndirectedGraph;
import graph.GraphException;
import java.util.*;

public class GameMap {

    private final GraphADT graph;
    private final String[] nodeNames;
    private final double[] nodeX;            // x coordinates
    private final double[] nodeY;            // y coordinates
    private final String[] nodeLabels;       // optional labels

    private static final int NUM_NODES = 16;

    public GameMap() {
        graph = new UndirectedGraph(NUM_NODES);

        nodeNames = new String[NUM_NODES];
        nodeX = new double[NUM_NODES];
        nodeY = new double[NUM_NODES];
        nodeLabels = new String[NUM_NODES];

        initializeNodes();
        initializeConnections();
    }

    // Private helper method to manually place nodes
    private void initializeNodes() {
        // it took me a nearly an hour :xdd:
        addNode(15, "office", 353, 500);
        addNode(0,  "top_top",  320, 30);

        addNode(2, "top_left", 160, 111);
        addNode(1, "top_right", 320, 111);

        addNode(6,  "center_left_top",    160, 200);
        addNode(9,  "center_left_bottom",  160, 266);

        addNode(5,  "center_mid", 269, 200);

        addNode(3,  "center_right_top",  420, 140);
        addNode(7,  "center_right_bottom",  420, 244);

        addNode(4,  "right_mid",  519, 168);
        addNode(10,  "right_bottom",  520, 280);

        addNode(8,  "left_mid", 74, 263);
        addNode(11, "left_bottom",  75, 361);

        addNode(12, "bottom_left",  192, 341);
        addNode(13, "bottom_mid", 262, 414);
        addNode(14, "department_of_defense", 420, 401);
    }

    private void addNode(int id, String name, double x, double y) {
        nodeNames[id] = name;
        nodeX[id] = x;
        nodeY[id] = y;
    }

    // Manually connect the nodes according to map
    private void initializeConnections() {
        try {
            // This took me another half an hour :xdd:
            // 0
            connect(0, 1);
            // 1
            connect(1, 2);
            connect(1, 3);
            connect(1, 5);
            // 2
            connect(2, 6);
            connect(2, 11);
            // 3
            connect(3, 4);
            connect(3, 7);
            // 4
            connect(4, 10);
            // 5
            connect(5, 6);
            // 6
            connect(6, 9);
            // 7
            connect(7, 9);
            connect(7, 10);
            // 8
            connect(8, 9);
            connect(8, 11);
            // 9
            connect(9, 12);
            // 10
            connect(10, 14);
            // 11
            connect(11, 12);
            // 12
            connect(12, 13);
            // 13
            connect(13, 15);
            // 14
            connect(14, 15);

        } catch (GraphException e) {
            System.err.println("Map connection error: " + e.getMessage());
        }
    }

    /**
     * Connect two nodes as undirectional graph
     * @param id1 The ID of the first node
     * @param id2 The ID of the second node
     * @throws GraphException Fails
     */
    private void connect(int id1, int id2) throws GraphException {
        GraphNode n1 = graph.getNode(id1);
        GraphNode n2 = graph.getNode(id2);
        graph.insertEdge(n1, n2, 'p');   // 'p' = path
    }

    // API for other classes

    /**
     * Gives a List of connecting nodes
     * @param id The ID of the node desired
     * @return A List of node ID in Integer
     */
    public List<Integer> getConnections(int id) {
        if (id == -1) return List.of();

        try {
            Iterator it = graph.incidentEdges(graph.getNode(id));
            List<Integer> connected = new ArrayList<>();
            while (it.hasNext()) {
                GraphEdge edge = (GraphEdge) it.next();
                int otherId = (edge.firstEndpoint().getName() == id) ?
                        edge.secondEndpoint().getName() :
                        edge.firstEndpoint().getName();
                connected.add(otherId);
            }
            return connected;
        } catch (GraphException e) {
            return List.of();
        }
    }

    /**
     * Returns the GameMapNode object given the node ID
     * @param id The ID of the desired node
     * @return The GameMapNode Object
     */
    public GameMapNode getNode(int id) {
        if (id == -1) return null;
        return new GameMapNode(id, nodeNames[id], nodeX[id], nodeY[id], nodeLabels[id]);
    }

    public GraphADT getGraph() {
        return graph;
    }
}