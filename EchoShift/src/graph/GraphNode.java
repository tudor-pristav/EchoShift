package graph;

/**
 * This class represents a node of a graph.
 * @author Yasmine Suojhayer
 */
public class GraphNode {
	
	//The instance variables for the class.//
	private final int nodeName;
	private boolean nodeMark;
	
	/**
	 * This is the constructor of the GraphNode class.
	 * @param name The integer name of the node. Name is between 0 and (n-1) where n is the total number of nodes in the Graph.
	 */
	public GraphNode(int name){
		nodeName = name;
		nodeMark = false;
	}
	
	/**
	 * This method is used to change the mark of the GraphNode.
	 * @param mark The new boolean mark for the GraphNode.
	 */
	public void setMark(boolean mark){
		nodeMark = mark;
	}
	
	/**
	 * This method returns the mark on the GraphNode.
	 * @return The mark boolean of the node.
	 */
	public boolean getMark() {
		return nodeMark;
	}
	
	/**
	 * This method returns the name of the node.
	 * @return The integer name of the node.
	 */
	public int getName() {
		return nodeName;
	}
	
}
