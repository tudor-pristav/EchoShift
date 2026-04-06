package graph;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents an undirected graph as an adjacency list.
 * @author Yasmine Suojhayer
 */
public class UndirectedGraph implements GraphADT {
	
	//The instance variables for the class.//
	private final GraphNode[] vertices;
	private final ArrayList[] edges;
	private final int numberOfNodes;
	
	/**
	 * This is the constructor of the class it will create the adjacency list holding the graph information.
	 * It creates two arrays one will hold the integer name of the GraphNode in the corresponding index.
	 * The other array will be an empty array that will hold the edges attached to the corresponding GraphNode.
	 * @param n The number of nodes in the graph.
	 */
	public UndirectedGraph(int n){
		numberOfNodes = n;
		vertices = new GraphNode[n];
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			vertices[i] = new GraphNode(i);
			edges[i] = new ArrayList<GraphEdge>(0);
		}
	}
	
	/**
	 * This method inserts a GraphEdge to its correct position in the adjacency list.
	 * @param u The first GraphNode endpoint of the edge.
	 * @param v The second GraphNode endpoint of the edge.
	 * @param edgeType The character used to specify the type of edge.
	 */
	public void insertEdge(GraphNode u, GraphNode v, char edgeType) throws GraphException {
		if ((u.getName() >= numberOfNodes) || (v.getName() >= numberOfNodes)) {
			throw new GraphException("One or both of the nodes are not in the graph.");
		} else {
			Iterator<GraphEdge> iter = edges[u.getName()].iterator();
			if (!iter.hasNext()) {
				edges[u.getName()].add(new GraphEdge(u, v, edgeType));
				edges[v.getName()].add(new GraphEdge(v, u, edgeType));
			} else {
				GraphEdge edge = iter.next();
				while ((iter.hasNext()) && (edge.secondEndpoint().getName() != v.getName())) {
					edge = iter.next();
				}
				if ((edge == null) || (!iter.hasNext())) {
					edges[u.getName()].add(new GraphEdge(u, v, edgeType));
					edges[v.getName()].add(new GraphEdge(v, u, edgeType));
				} else {
					throw new GraphException("This edge is already in the list.");
				}	
			}		
		}
	}

	/**
	 * This method returns the node with the specified name.
	 * @param name The name of the node to be returned.
	 * @return The node with the indicated name.
	 * @throws GraphException If the node is not in the graph.
	 */
	public GraphNode getNode(int name) throws GraphException {
		if (name >= numberOfNodes) {
			throw new GraphException("This node is not in the graph.");
		} else {
			return vertices[name];
		}
	}
	
	/**
	 * This method returns an iterator that will list all edges incident on the specified node.
	 * @param u The node whose list of edges is returned.
	 * @return An iterator with all edges incident on node u.
	 * @throws GraphException If u is not in the graph an exception is thrown.
	 */
	public Iterator incidentEdges(GraphNode u) throws GraphException {
		if (u.getName() >= numberOfNodes) {
			throw new GraphException("This node is not in the graph.");
		} else {
			return edges[u.getName()].iterator();
		}
	}
	
	/**
	 * This method finds the edge connecting two nodes.
	 * @param u One of the endpoints of the edge being serched for.
	 * @param v One of the endpoints of the edge being serched for.
	 * @return The edge connecting u and v if it exists.
	 * @throws GraphException If one or more of the nodes are not in the list or the edge does not exist an exception is thrown.
	 */
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if ((u.getName() >= numberOfNodes) || (v.getName() >= numberOfNodes)) {
			throw new GraphException("One or both of these nodes are not in the graph.");
		} else {
			Iterator<GraphEdge> iter = edges[u.getName()].iterator();
			if (iter.hasNext() == false) {
				throw new GraphException("This edge does not exist.");
			} else {
				GraphEdge edge = iter.next();
				while ((iter.hasNext() == true) && (edge.secondEndpoint() != v)) {
					edge = iter.next();
				}
				if ((edge == null)  || (edge.secondEndpoint() != v)) {
					throw new GraphException("This edge does not exist.");
				} else {
					return edge;
				}	
			}		
		}
	}
	
	/**
	 * This method checks if two nodes are adjacent.
	 * @param u One of the endpoints of the edge being searched for.
	 * @param v One of the endpoints of the edge being searched for.
	 * @return true is returned if the nodes are connected by an edge, false otherwise.
	 * @throws GraphException If One or more of the nodes are not in the graph.
	 */
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if ((u.getName() >= numberOfNodes) || (v.getName() >= numberOfNodes)) {
			throw new GraphException("One or both of these nodes are not in the graph.");
		} else {
			try {
				this.getEdge(u, v);
				return true;
			} catch (GraphException e) {
				return false;
			}
		}	
	}

}
