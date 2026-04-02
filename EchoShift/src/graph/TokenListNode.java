package graph;

/**
 * This class 
 * @author Yasmine Suojhayer
 */
public class TokenListNode {
	
	//The instance variables for the class.//
	private char token;
	private int numberLeft;
	private TokenListNode next;
	
	/**
	 * This is the constructor that will create a node for a singly liked list and store a GraphEdge within.
	 * @param theEdge the GraphEdge that will be stored in the list.
	 */
	public TokenListNode(char tokenType, int numberOfTokens) {
		token = tokenType;
		numberLeft = numberOfTokens;
		next = null;
	}
		
	/**
	 * This method returns the next node in the list or null if the node is the last in the list.
	 * @return The next GraphEdge in the list or null.
	 */
	public TokenListNode getNext() {
		return next;
	}
	
	/**
	 * This method sets the next node in the singly linked list.
	 * @param nextRecord the node that is added to the list.
	 */
	public void setNext(TokenListNode nextNode) {
		next = nextNode;
	}
	
	/**
	 * This method returns the number of tokens left of this type;
	 * @return The integer number of tokens left.
	 */
	public boolean tokenLeft() {
		if (numberLeft > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method decrements the number of tokens of this type;
	 */
	public void decrementToken() {
		numberLeft = numberLeft - 1;
	}
	
	/**
	 * This method increments the number of tokens of this type;
	 */
	public void incrementToken() {
		numberLeft = numberLeft - 1;
	}

	/**
	 * This method returns the token type stored in the node.
	 * @return data the record stored in the node.
	 */
	public char getToken() {
		return token;
	}
}
