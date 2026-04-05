package echoshift.UI;

import echoshift.backend.GameMap;
import echoshift.backend.GameMapNode;
import echoshift.backend.Entity;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Objects;

/**
 * This class creates the visuals for the map and the enemy tracker.
 */
public class MapRenderer {

    private final Pane overlay;
    private final StackPane mapPane;

    private ImageView entityIndicator = new ImageView(new Image("/echoshift/images/entity-tracker.gif", 150, 150, true, true));

    private java.util.function.Consumer<Integer> nodeClickHandler;

    /**
     * This creates the pane that with show nodes on the graph.
     *
     * @param gameMap The graph logically representing the game map.
     */
    public MapRenderer(GameMap gameMap) {
        Image mapImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/echoshift/images/map.png")));

        ImageView background = new ImageView(mapImage);
        background.setPreserveRatio(true);

        overlay = new Pane();
        mapPane = new StackPane(background, overlay);
        mapPane.setMaxSize(558, 543);
        mapPane.setAlignment(Pos.CENTER);

        renderStaticNodes(gameMap);
    }

    /**
     * This constructor takes a game map and places the image of the nodes based on their coordinates.
     *
     * @param gameMap The graph logically representing the game map.
     */
    private void renderStaticNodes(GameMap gameMap) {
        for (int nodeID = 0; nodeID < 16; nodeID++) {
            GameMapNode node = gameMap.getNode(nodeID);
            if (node == null) continue;

            Circle circle = new Circle(node.getNodeX(), node.getNodeY(), 18);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.RED);
            circle.setStrokeWidth(4);

            int finalId = node.getID();
            circle.setOnMouseClicked(e -> {
                System.out.println("Clicked node: " + finalId);

                if (nodeClickHandler != null) {
                    nodeClickHandler.accept(finalId);
                }

                // Debug highlight
                for (javafx.scene.Node n : overlay.getChildren()) {
                    if (n instanceof Circle c && c.getStroke() == Color.GREEN) {
                        c.setStroke(Color.RED);
                    }
                }
                circle.setStroke(Color.GREEN);
            });

            overlay.getChildren().add(circle);
        }
    }

    /**
     * This class add the visual representation of the enemy to the game screen.
     * The Entity is always on the screen but invisible unless the canner is active.
     *
     * @param entity The enemy of the game level.
     */
    public void addEntity(Entity entity) {
        entityIndicator.setOpacity(0.0);
        entityIndicator.setMouseTransparent(true);
        overlay.getChildren().add(entityIndicator);
        updateEntityPosition(entity);
    }

    /**
     * Updates the enemy's indicator on the game screen.
     *
     * @param entity The enemy of the game level.
     */
    public void updateEntityPosition(Entity entity) {
        if (entityIndicator != null) {
            GameMapNode node = new GameMap().getNode(entity.getCurrentRoomId());
            if (node != null) {
                entityIndicator.setTranslateX(node.getNodeX()-77);
                entityIndicator.setTranslateY(node.getNodeY()-77);
            }
        }
    }

    /**
     * This class sets the opacity of the entity to full.
     * It is used when the scanner is activated
     */
    public void scan() {
        entityIndicator.setOpacity(1.0);
    }

    /**
     * This class sets the opacity of the entity to zero.
     * It is used when the scanner time runs out.
     */
    public void endScan() {
        entityIndicator.setOpacity(0.0);
    }

    /**
     * This returns the StackPane containing the background along with its nodes.
     *
     * @return The StackPane containing the background along with its nodes.
     */
    public StackPane getMapPane() {
        return mapPane;
    }

    /**
     * This class gives a listener for when the player clicks on a node.
     * This tracks which node is selected.
     *
     * @param handler A listener for the mouse click event.
     */
    public void setNodeClickHandler(java.util.function.Consumer<Integer> handler) {
        this.nodeClickHandler = handler;
    }
}