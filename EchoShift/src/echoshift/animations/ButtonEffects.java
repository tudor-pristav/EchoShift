package echoshift.animations;
import javafx.scene.control.Button;

public class ButtonEffects {
    public static void hoverAnimation(Button button) {
        button.setTranslateY(-3);
        button.setOnMouseEntered(e -> button.setTranslateY(-5));
        button.setOnMouseExited(e -> button.setTranslateY(-3));

    }
    public static void clickAnimation(Button button){
        button.setOnMousePressed(e -> {SoundEffects.playClickSound();button.setTranslateY(0); });
        button.setOnMouseReleased(e -> button.setTranslateY(-5));

    }
}
