package echoshift.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameMap class.
 */
@DisplayName("GameMap Tests")
class GameMapTest {

    private GameMap gameMap;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap();
    }

    // Test node existence

    @Test
    @DisplayName("All 16 nodes should exist")
    void allNodesExist() {
        for (int id = 0; id < 16; id++) {
            GameMapNode node = gameMap.getNode(id);
            assertNotNull(node, "Node " + id + " should exist");
            assertEquals(id, node.getID(), "Node ID should match");
        }
    }

    @Test
    @DisplayName("getNode returns null for invalid ID")
    void getNodeInvalidIdReturnsNull() {
        assertNull(gameMap.getNode(-1));
        assertNull(gameMap.getNode(100));
    }

    // Test node connections

    @Test
    @DisplayName("getConnections returns empty list for invalid ID")
    void getConnectionsInvalidIdReturnsEmpty() {
        List<Integer> connections = gameMap.getConnections(-1);
        assertTrue(connections.isEmpty(), "Invalid ID should return empty list");
    }

    @Test
    @DisplayName("Node 15 (office) should be connected to nodes 13 and 14")
    void officeConnections() {
        List<Integer> connections = gameMap.getConnections(15);
        assertEquals(2, connections.size(), "Office should have 2 connections");
        assertTrue(connections.contains(13));
        assertTrue(connections.contains(14));
    }

    @Test
    @DisplayName("Node 0 (top_top) should be connected only to node 1")
    void topTopConnections() {
        List<Integer> connections = gameMap.getConnections(0);
        assertEquals(1, connections.size());
        assertTrue(connections.contains(1));
    }

    @Test
    @DisplayName("Node 8 (left_mid) should be connected to nodes 9 and 11")
    void leftMidConnections() {
        List<Integer> connections = gameMap.getConnections(8);
        assertEquals(2, connections.size());
        assertTrue(connections.contains(9));
        assertTrue(connections.contains(11));
    }

    // Parameterized test for multiple nodes
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    @DisplayName("Each node should have at least one connection (except possibly isolated but none should be isolated)")
    void everyNodeHasAtLeastOneConnection(int nodeId) {
        List<Integer> connections = gameMap.getConnections(nodeId);
        assertFalse(connections.isEmpty(), "Node " + nodeId + " should have at least one connection");
    }

    // Test connection correctness

    @Test
    @DisplayName("Connections should be bidirectional (undirected graph)")
    void connectionsAreBidirectional() {
        // Test a sample of connections
        int[][] testPairs = {
                {0, 1}, {1, 2}, {1, 3}, {1, 5},
                {2, 6}, {3, 4}, {5, 6}, {8, 9}, {12, 13}, {13, 15}
        };

        for (int[] pair : testPairs) {
            int a = pair[0];
            int b = pair[1];
            List<Integer> connA = gameMap.getConnections(a);
            List<Integer> connB = gameMap.getConnections(b);
            assertTrue(connA.contains(b), a + " should be connected to " + b);
            assertTrue(connB.contains(a), b + " should be connected to " + a);
        }
    }

    // Test node data

    @Test
    @DisplayName("Node coordinates should be set correctly")
    void nodeCoordinates() {
        GameMapNode office = gameMap.getNode(15);
        assertEquals(353, office.getNodeX(), 0.01);
        assertEquals(500, office.getNodeY(), 0.01);

        GameMapNode topTop = gameMap.getNode(0);
        assertEquals(320, topTop.getNodeX(), 0.01);
        assertEquals(30, topTop.getNodeY(), 0.01);
    }

    @Test
    @DisplayName("Node names should match expected values")
    void nodeNames() {
        assertEquals("office", gameMap.getNode(15).getName());
        assertEquals("top_top", gameMap.getNode(0).getName());
        assertEquals("department_of_defense", gameMap.getNode(14).getName());
        assertEquals("bottom_mid", gameMap.getNode(13).getName());
    }

    // Test exceptions

    @Test
    @DisplayName("Getting connections for node returns mutable list (can be modified without affecting graph)")
    void connectionsListIsMutable() {
        List<Integer> connections = gameMap.getConnections(1);
        int originalSize = connections.size();
        connections.add(999); // should not affect internal graph
        assertEquals(originalSize + 1, connections.size());
        // Verify graph still returns original connections
        List<Integer> freshConnections = gameMap.getConnections(1);
        assertEquals(originalSize, freshConnections.size());
        assertFalse(freshConnections.contains(999));
    }

    @Test
    @DisplayName("Multiple calls to getNode with same ID return equivalent objects (not necessarily same instance)")
    void getNodeReturnsEquivalentObjects() {
        GameMapNode node1 = gameMap.getNode(5);
        GameMapNode node2 = gameMap.getNode(5);
        assertEquals(node1.getID(), node2.getID());
        assertEquals(node1.getName(), node2.getName());
        assertEquals(node1.getNodeX(), node2.getNodeX());
        assertEquals(node1.getNodeY(), node2.getNodeY());
    }
}