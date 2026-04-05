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

public class MapRenderer {

    private final Pane overlay;
    private final StackPane mapPane;

    private ImageView entityIndicator = new ImageView(new Image("/echoshift/images/entity-tracker.gif", 150, 150, true, true));

    private java.util.function.Consumer<Integer> nodeClickHandler;

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

    public void addEntity(Entity entity) {
        entityIndicator.setOpacity(0.0);
        entityIndicator.setMouseTransparent(true);
        overlay.getChildren().add(entityIndicator);
        updateEntityPosition(entity);
    }

    public void updateEntityPosition(Entity entity) {
        if (entityIndicator != null) {
            GameMapNode node = new GameMap().getNode(entity.getCurrentRoomId());
            if (node != null) {
                entityIndicator.setTranslateX(node.getNodeX()-77);
                entityIndicator.setTranslateY(node.getNodeY()-77);
            }
        }
    }

    public void scan() {
        entityIndicator.setOpacity(1.0);
    }

    public void endScan() {
        entityIndicator.setOpacity(0.0);
    }

    public StackPane getMapPane() {
        return mapPane;
    }

    public void clearEnemies() {
        if (entityIndicator != null) overlay.getChildren().remove(entityIndicator);
        entityIndicator = null;
    }
    public void setNodeClickHandler(java.util.function.Consumer<Integer> handler) {
        this.nodeClickHandler = handler;
    }
}