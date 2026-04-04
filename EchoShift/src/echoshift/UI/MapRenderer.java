package echoshift.UI;

import echoshift.backend.GameMap;
import echoshift.backend.GameMapNode;
import echoshift.backend.Entity;
import echoshift.backend.Listener;

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

    private Circle entityIndicator;
    private Circle listenerIndicator;

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
                // Visual feedback for debugging
                if (nodeClickHandler != null) {
                    nodeClickHandler.accept(finalId);
                }

                // Debug highlight
                for (javafx.scene.Node n : overlay.getChildren()) {
                    if (n instanceof Circle c && c.getStroke() == Color.RED) {
                        c.setFill(Color.TRANSPARENT);
                    }
                }

                circle.setFill(Color.GREEN);
            });

            overlay.getChildren().add(circle);
        }
    }

    public void addEntity(Entity entity) {
        entityIndicator = createIndicator(Color.ORANGE);
        overlay.getChildren().add(entityIndicator);
        updateEntityPosition(entity);
    }

    public void addListener(Listener listener) {
        listenerIndicator = createIndicator(Color.RED);
        overlay.getChildren().add(listenerIndicator);
        updateListenerPosition(listener);
    }

    private Circle createIndicator(Color color) {
        Circle c = new Circle(12);
        c.setFill(color);
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(3);
        return c;
    }

    public void updateEntityPosition(Entity entity) {
        if (entityIndicator != null) {
            GameMapNode node = new GameMap().getNode(entity.getCurrentRoomId());
            if (node != null) {
                entityIndicator.setTranslateX(node.getNodeX());
                entityIndicator.setTranslateY(node.getNodeY());
            }
        }
    }

    public void updateListenerPosition(Listener listener) {
        if (listenerIndicator != null) {
            GameMapNode node = new GameMap().getNode(listener.getCurrentRoomId());
            if (node != null) {
                listenerIndicator.setTranslateX(node.getNodeX());
                listenerIndicator.setTranslateY(node.getNodeY());
            }
        }
    }

    public StackPane getMapPane() {
        return mapPane;
    }

    public void clearEnemies() {
        if (entityIndicator != null) overlay.getChildren().remove(entityIndicator);
        if (listenerIndicator != null) overlay.getChildren().remove(listenerIndicator);
        entityIndicator = null;
        listenerIndicator = null;
    }
    public void setNodeClickHandler(java.util.function.Consumer<Integer> handler) {
        this.nodeClickHandler = handler;
    }
}