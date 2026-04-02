// MapRenderer.java
package echoshift.UI;

import echoshift.backend.GameMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class MapRenderer {

    private final Pane overlay;
    private final StackPane mapPane;
    private final Circle[] nodeCircles = new Circle[16];

    public MapRenderer(GameMap gameMap) {
        // Background image - adjust path if needed
        Image mapImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/echoshift/images/map.png")));
        ImageView background = new ImageView(mapImage);
        background.setPreserveRatio(true);

        overlay = new Pane();
        overlay.minWidth(558);
        overlay.minHeight(543);
        overlay.prefWidth(558);
        overlay.prefHeight(543);
        mapPane = new StackPane(background, overlay);

        renderStaticNodes(gameMap);
    }

    private void renderStaticNodes(GameMap gameMap) {
        for (int nodeID=0; nodeID<16; nodeID++) {
            GameMap.GameMapNode node = gameMap.getNode(nodeID);
            if (node == null) continue;

            // Drawing circles
            Circle circle = new Circle(node.x(), node.y(), 18);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.RED);
            circle.setStrokeWidth(4);

            // Click the circles
            int finalName = node.getID();
            circle.setOnMouseClicked(e -> {
                System.out.println("Player clicked node: " + finalName);
                for (Circle circles: nodeCircles){
                    circles.setFill(Color.TRANSPARENT);
                }
                circle.setFill(Color.GREEN);
            });
            nodeCircles[nodeID] = circle;
            overlay.getChildren().add(circle);
        }
    }

    public StackPane getMapPane() {
        return mapPane;
    }

    // Called every frame when Listener (enemy) moves
    public void moveEnemyIndicator(int nodeID, javafx.scene.Node indicator) {
        GameMap.GameMapNode node = new GameMap().getNode(nodeID);
        if (node != null) {
            indicator.setTranslateX(node.x());
            indicator.setTranslateY(node.y());
        }
    }
}