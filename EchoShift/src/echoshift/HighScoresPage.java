package echoshift;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *High scores page for the game Echo Shift
 * @version 1.0.0
 * @author Bob Zhang
 */

public class HighScoresPage {

    /**
     *Method to set up and format the high score page.
     */
    public Parent createHighScoresPage() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: transparent;");

        setupBackground(root);

        Button backBtn = createBackButton();
        VBox mainLayout = createMainLayout();

        root.getChildren().addAll(mainLayout, backBtn);

        return root;
    }

    /**
     *Method to Load and bind the background image to the root pane.
     *@param root The root pane of the app
     */
    private void setupBackground(StackPane root) {
        Image backgroundImage = null;
        try {
            String imagePath = getClass().getResource("/src/echoshift/images/bg3.png").toExternalForm();
            backgroundImage = new Image(imagePath);
        } catch (Exception e) {
            System.err.println("Could not find background image: /src/echoshift/images/bg3.png");
            root.setStyle("-fx-background-color: #8c8c8c;");
        }

        if (backgroundImage != null) {
            ImageView backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setPreserveRatio(true);
            backgroundImageView.setSmooth(true);
            backgroundImageView.fitWidthProperty().bind(root.widthProperty());
            backgroundImageView.fitHeightProperty().bind(root.heightProperty());
            root.getChildren().add(backgroundImageView);
        }
    }

    /**
     *Method creates and formats the back button.
     */
    private Button createBackButton() {
        Button backBtn = new Button("Back");
        backBtn.setPrefSize(140, 45);
        backBtn.setFont(Font.font("Arial", 16));
        backBtn.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: transparent; -fx-cursor: hand;");

        StackPane.setAlignment(backBtn, Pos.BOTTOM_LEFT);
        StackPane.setMargin(backBtn, new Insets(20));

        return backBtn;
    }

    /**
     *Method creates the vertical layout containing the title and the scrollable scores.
     */
    private VBox createMainLayout() {
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(30, 0, 0, 0));

        Label title = new Label("Top Scores");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));

        VBox scoreList = createScoreList();
        ScrollPane scrollPane = createScrollPane(scoreList);

        mainLayout.getChildren().addAll(title, scrollPane);
        return mainLayout;
    }

    /**
     *Methods generate the list of high scores, placeholder stuff for now.
     */
    private VBox createScoreList() {
        VBox scoreList = new VBox(15);
        scoreList.setAlignment(Pos.TOP_CENTER);
        scoreList.setPadding(new Insets(20));
        scoreList.setStyle("-fx-background-color: #e0e0e0;");

        // Generate 10 entries
        for (int i = 1; i <= 10; i++) {
            scoreList.getChildren().add(createScoreEntry(i));
        }

        return scoreList;
    }

    /**
     *Method creates a score row for every rank.
     * @param rank Represents the rank listing based on how the score is.
     */
    private Node createScoreEntry(int rank) {
        if (rank <= 2) {
            // High-style text for top 2
            String name = (rank == 1) ? "ALEX" : "JORDAN";
            Label scoreEntry = new Label("#" + rank + " Username : " + name + " - " + (5000 / rank));
            scoreEntry.setFont(Font.font("Arial", 28));
            return scoreEntry;
        } else {
            // Dark bar style for ranks 3-10
            StackPane barContainer = new StackPane();
            Rectangle bar = new Rectangle(500, 40, Color.web("#404040"));

            Label barLabel = new Label("#" + rank + " Username : XXXXX");
            barLabel.setTextFill(Color.WHITE);

            barContainer.getChildren().addAll(bar, barLabel);
            return barContainer;
        }
    }

    /**
     *Method creates a scroll bar for the settings panel.
     *@param node Stores the panel that will require the scroll box feature.
     */
    private ScrollPane createScrollPane(javafx.scene.Node node) {
        ScrollPane scrollPane = new ScrollPane(node);
        scrollPane.setMaxSize(600, 400);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;");
        return scrollPane;
    }
}