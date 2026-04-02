package graph;

/**
 * This class represents an edge of a graph.
 *
 * @author Yasmine Suojhayer
 */
public class GraphEdge {
	
	//The instance variables for the class.//
	private GraphNode firstEndpoint;
	private GraphNode secondEndpoint;
	private char nodeType;
	private String nodeLabel;
	
	/**
	 * This class constructor represents an edge without a label.
	 *
	 * @param u The first GraphNode endpoint of the edge.
	 * @param v The second GraphNode endpoint of the edge.
	 * @param type The character type of the node.
	 */
	public GraphEdge(GraphNode u, GraphNode v, char type) {
		firstEndpoint = u;
		secondEndpoint = v;
		nodeType = type;
	}
	
	/**
	 * This class constructor represents an edge with a label.
	 *
	 * @param u The first GraphNode endpoint of the edge.
	 * @param v second GraphNode endpoint of the edge.
	 * @param type The character type of the node.
	 * @param label The String label of the node. 
	 */
	public GraphEdge(GraphNode u, GraphNode v, char type, String label) {
		firstEndpoint = u;
		secondEndpoint = v;
		nodeType = type;
		nodeLabel = label;
	}
	
	/**
	 * This method returns the first endpoint of the edge.
	 *
	 * @return The GraphNode first endpoint of the edge.
	 */
	public GraphNode firstEndpoint() {
		return firstEndpoint;
	}
	
	/**
	 * This method returns the second endpoint of the edge.
	 *
	 * @return The GraphNode second endpoint of the edge.
	 */
	public GraphNode secondEndpoint() {
		return secondEndpoint;
	}
	
	/**
	 * This method returns the type of the edge.
	 *
	 * @return The character type of the edge.
	 */
	public char getType() {
		return nodeType;
	}
	
	/**
	 * This method returns the label of the edge.
	 *
	 * @return The String label of the edge. 
	 */
	public String getLabel() {
		return nodeLabel;
	}
	
	/**
	 * This method changes the label of the edge.
	 *
	 * @param newLabel The new string label of the edge.
	 */
	public void setLabel(String newLabel) {
		nodeLabel = newLabel;
	}
}
