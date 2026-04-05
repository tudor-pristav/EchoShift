package echoshift.backend;


public class GameMapNode {
    private final int id;
    private final String nodeName;
    private final double nodeX;
    private final double nodeY;
    private final String nodeLabel;

    /**
     * A GameMapNode record to pass data easier
     * @param id Node ID
     * @param name Name of the node for easier debug
     * @param x The x coordinate of the node placement on the window
     * @param y The y coordinate of the node placement on the window
     * @param label Any label string to be shown to the right of the node
     */
    public GameMapNode(int id, String name, double x, double y, String label) {
        this.id = id;
        this.nodeName = name;
        this.nodeX = x;
        this.nodeY = y;
        this.nodeLabel = label;
    }

    public int getID() {
        return id;
    }
    public String getNodeName() {
        return nodeName;
    }
    public double getNodeX() {
        return nodeX;
    }
    public double getNodeY() {
        return nodeY;
    }
    public String getNodeLabel() {
        return nodeLabel;
    }
}
