package echoshift;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Settings Page for EchoShift game
 *The volume slider is visually controlled by the volumeSetting method.
 *The Keyboard navigation option is visually controlled by the keyboardNavSetting method.
 *The volume slider is visually controlled by the fontSizeSetting method.
 *
 * @version 1.0.0
 * @author Bob Zhang
 */
public class SettingsPage {

    /**
     *Method to set up and format the settings page.
     */
    public Parent createSettingsPage() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: transparent;");

        setupBackground(root);

        Button backBtn = createBackButton();
        VBox settingsPanel = createSettingsPanel();
        ScrollPane scrollPane = createScrollPane(settingsPanel);

        StackPane.setAlignment(backBtn, Pos.BOTTOM_LEFT);
        StackPane.setMargin(backBtn, new Insets(20));

        root.getChildren().addAll(scrollPane, backBtn);

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
            backgroundImageView.setFitWidth(root.getWidth());
            backgroundImageView.setFitHeight(root.getHeight());
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
        return backBtn;
    }

    /**
     *Method builds and formats the settings tab box.
     */
    private VBox createSettingsPanel() {
        VBox settingsPanel = new VBox(35);
        settingsPanel.setAlignment(Pos.TOP_CENTER);
        settingsPanel.setPadding(new Insets(40, 60, 40, 60));
        settingsPanel.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: black;");

        Label title = new Label("Settings");
        title.setFont(Font.font("Arial", 36));

        GridPane grid = createSettingsGrid();

        settingsPanel.getChildren().addAll(title, grid);

        return settingsPanel;
    }

    /**
     *Method adds the settings to the settings pane visually, and provides basic formatting values.
     */
    private GridPane createSettingsGrid() {
        GridPane grid = new GridPane();
        grid.setVgap(25);
        grid.setHgap(40);
        grid.setAlignment(Pos.CENTER);

        volumeSetting(grid, 0);
        keyboardNavSetting(grid, 1);
        fontSizeSetting(grid, 2);

        return grid;
    }

    /**
     *Method creates the volume slider and adds it to the specified row in the grid for formatting.
     * @param grid Specifies which settings grid box object to store the volume slider within.
     * @param row Specifies which position the volume slider will appear in the settings grid box.
     */
    private void volumeSetting(GridPane grid, int row) {
        Label volLabel = new Label("Volume");
        volLabel.setFont(Font.font("Arial", 20));

        VBox sliderBox = new VBox(5);
        Slider volSlider = new Slider(0, 200, 100);
        volSlider.setPrefWidth(250);
        volSlider.setMajorTickUnit(50);
        volSlider.setShowTickMarks(true);

        BorderPane sliderLabels = new BorderPane();
        sliderLabels.setLeft(new Label("0%"));
        sliderLabels.setRight(new Label("200%"));
        sliderBox.getChildren().addAll(volSlider, sliderLabels);

        grid.add(volLabel, 0, row);
        grid.add(sliderBox, 1, row);
    }

    /**
     *Method creates the keyboard navigation checkbox and adds it to the specified row in the grid for formatting.
     *@param grid Specifies which settings grid box object to store the checkbox within.
     *@param row Specifies which position the checkbox will appear in the settings grid box.
     */
    private void keyboardNavSetting(GridPane grid, int row) {
        Label navLabel = new Label("Keyboard Navigation");
        navLabel.setFont(Font.font("Arial", 20));

        CheckBox navCheck = new CheckBox();
        navCheck.setStyle("-fx-scale-x: 1.5; -fx-scale-y: 1.5;");

        grid.add(navLabel, 0, row);
        grid.add(navCheck, 1, row);
        GridPane.setMargin(navCheck, new Insets(0, 0, 0, 10)); // Align nicely
    }

    /**
     *Method creates the font size combobox and adds it to the specified row in the grid for formatting.
     *@param grid Specifies which settings grid box object to store the combobox within.
     *@param row Specifies which position the combobox will appear in the settings grid box.
     */
    private void fontSizeSetting(GridPane grid, int row) {
        Label fontLabel = new Label("Font Size");
        fontLabel.setFont(Font.font("Arial", 20));

        ComboBox<String> fontCombo = new ComboBox<>();
        fontCombo.getItems().addAll("50%", "100%", "150%", "200%");
        fontCombo.setValue("100%");
        fontCombo.setStyle("-fx-font-size: 16px; -fx-background-color: white; -fx-border-color: black;");

        grid.add(fontLabel, 0, row);
        grid.add(fontCombo, 1, row);
    }

    /**
     *Method creates a scroll bar for the settings panel.
     *@param node Stores the panel that will require the scroll box feature.
     */
    private ScrollPane createScrollPane(javafx.scene.Node node) {
        ScrollPane scrollPane = new ScrollPane(node);
        scrollPane.setMaxSize(750, 450);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;");
        return scrollPane;
    }
}