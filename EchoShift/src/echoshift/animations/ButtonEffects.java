package echoshift.animations;

import javafx.scene.control.Button;

/**
 * Utility class for applying simple hover and click animations to buttons.
 *
 * @author Tudor Mihai Pristav
 */
public class ButtonEffects {

    /**
     * Applies a hover animation that slightly lifts the button on mouse enter.
     *
     * @param button the button to animate
     */
    public static void hoverAnimation(Button button) {
        button.setTranslateY(-3);
        button.setOnMouseEntered(e -> button.setTranslateY(-5));
        button.setOnMouseExited(e -> button.setTranslateY(-3));
    }

    /**
     * Applies a click animation with sound and press effect.
     *
     * @param button the button to animate
     */
    public static void clickAnimation(Button button) {
        button.setOnMousePressed(e -> {
            SoundEffects.playClickSound();
            button.setTranslateY(0);
        });
        button.setOnMouseReleased(e -> button.setTranslateY(-5));
    }
}