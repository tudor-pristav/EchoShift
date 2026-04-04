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
        // it took me a nearly an hours :xdd:
        addNode(0, "office", 575, 580);
        addNode(1,  "top_top",  542, 110);

        addNode(2, "top_left", 382, 191);
        addNode(3, "top_right", 542, 191);

        addNode(6,  "center_left_top",    382, 280);
        addNode(10,  "center_left_bottom",  382, 344);

        addNode(7,  "center_mid", 491, 280);

        addNode(4,  "center_right_top",  642, 220);
        addNode(8,  "center_right_bottom",  642, 322);

        addNode(5,  "right_mid",  741, 248);
        addNode(11,  "right_bottom",  742, 360);

        addNode(9,  "left_mid", 296, 343);
        addNode(13, "left_bottom",  297, 441);

        addNode(12, "bottom_left",  414, 421);
        addNode(14, "bottom_mid", 484, 494);
        addNode(15, "department_of_defense", 642, 481);
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
            // 1
            connect(1,3);
            // 2
            connect(2,3);
            connect(2,6);
            connect(2,13);
            // 3
            connect(3,7);
            connect(3,4);
            // 4
            connect(4,5);
            connect(4,8);
            // 5
            connect(5,11);
            // 6
            connect(6,7);
            connect(6,10);
            // 7
            // 8
            connect(8,10);
            connect(8,11);
            // 9
            connect(9,10);
            connect(9,13);
            // 10
            connect(10,12);
            // 11
            connect(11,15);
            //12
            connect(12,13);
            connect(12,14);
            //13
            //14
            connect(14,0);
            //15
            connect(15,0);
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
}