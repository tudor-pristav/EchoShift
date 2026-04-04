// MapRenderer.java
package echoshift.UI;

import echoshift.backend.GameMap;
import echoshift.backend.GameMapNode;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
        mapPane = new StackPane(background, overlay);
        mapPane.setMaxSize(558,543);
        mapPane.setAlignment(Pos.CENTER);

        renderStaticNodes(gameMap);
    }

    private void renderStaticNodes(GameMap gameMap) {
        for (int nodeID=0; nodeID<16; nodeID++) {
            GameMapNode node = gameMap.getNode(nodeID);
            if (node == null) continue;

            // Drawing circles
            Circle circle = new Circle(node.getNodeX(), node.getNodeY(), 18);
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
        GameMapNode node = new GameMap().getNode(nodeID);
        if (node != null) {
            indicator.setTranslateX(node.getNodeX());
            indicator.setTranslateY(node.getNodeY());
        }
    }
}