package graph;

import java.util.Iterator;

public interface GraphADT {

  /**
   * Adds to the graph an edge connecting nodes u and v.
   * The type and label for this new edge are as indicated by the last parameters.
   * This method throws a GraphException if either node does not exist or if there is already an edge connecting the given vertices.
   *
   * @param u One of the endnodes for the edge.
   * @param v One of the endpoints for the edge.
   */
  public void insertEdge(GraphNode u, GraphNode v, char type) throws GraphException;

  /**
   * Returns the node with the specified name.
   * If no node with this name exists in the graph, the method throws a GraphException.
   *
   * @param u The node ID that should be returned.
   */
  public GraphNode getNode(int u) throws GraphException;

  /**
   * Returns a Java Iterator storing all the edges incident on node u.
   * It returns null if node u does not have any edges incident on it. A GraphException is thrown if u is not a node of this graph.
   *
   * @param u This node's neighbors will be returned in the Iterator.
   */
  public Iterator incidentEdges(GraphNode u) throws GraphException;

  /**
   * Returns the edge connecting nodes u and v.
   * Throws a GraphException if u or v are not nodes of this graph or if there is no edge between u and v.
   *
   * @param u One of the nodes being checked for a connection.
   * @param v One of the nodes being checked for a connection.
   */
  public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException;

  /**
   * Returns true if nodes u and v are adjacent; returns false otherwise.
   * Throws a GraphException if u or v are not nodes of this graph.
   *
   * @param u One of the nodes being checked for adjacency.
   * @param v One of the nodes being checked for adjacency.
   */
  public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException;
}
