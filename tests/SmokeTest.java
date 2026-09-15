import echoshift.App;
import echoshift.models.*;
import echoshift.services.AccountCreationService;
import echoshift.nightscripts.MainGameplay;
import echoshift.nightscripts.Night;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** Opens real JavaFX scenes and checks the end-of-round navigation in temporary storage. */
public class SmokeTest extends Application {
    private void screenshot(Stage stage, String name) throws Exception {
        stage.getScene().getRoot().applyCss();
        stage.getScene().getRoot().layout();
        var build = java.nio.file.Path.of(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        var output = build.resolve("screenshots");
        java.nio.file.Files.createDirectories(output);
        javax.imageio.ImageIO.write(javafx.embed.swing.SwingFXUtils.fromFXImage(
                stage.getScene().snapshot(null), null), "png", output.resolve(name + ".png").toFile());
    }
    public void start(Stage stage) {
        try {
            new App().start(stage);
            screenshot(stage, "menu");
            if (!stage.isShowing()) throw new AssertionError("Main menu did not open");
            UserAccount user = new AccountCreationService().createAccount("smoke", "test-only");
            Session session = new Session(user, new UserStatistics(), new PlayerPowerups(0, 0, 1));
            MainGameplay game = new MainGameplay(1, session);
            game.start(stage);
            screenshot(stage, "gameplay");
            var useLure = MainGameplay.class.getDeclaredMethod("instantLure");
            useLure.setAccessible(true);
            useLure.invoke(game);
            if (session.getPowerUps().getInstantLure() != 0) throw new AssertionError("Lure requires wrong inventory");
            var field = MainGameplay.class.getDeclaredField("currentNight");
            field.setAccessible(true);
            Night night = (Night) field.get(game);
            night.stopNight();
            night.stopNight();
            if (session.getCurrentStatistics().getGamesPlayed() != 1) throw new AssertionError("Duplicate save");
            if (session.getCurrentStatistics().getHighestLevel() != 1) throw new AssertionError("Interrupted night unlocked level");
            Button home = (Button) stage.getScene().getRoot().lookupAll(".button").stream()
                    .filter(n -> n instanceof Button b && b.getText().equals("Return to Player Home"))
                    .findFirst().orElseThrow();
            home.fire();
            if (!stage.getTitle().equals("Echo Shift - Player Home")) throw new AssertionError("Return navigation");
            echoshift.animations.SoundEffects.stop();
            echoshift.UI.PlayerLoginView login = new echoshift.UI.PlayerLoginView();
            new echoshift.controllers.PlayerLoginController(stage, login);
            stage.getScene().setRoot(login.createPlayerLoginPage());
            login.getUsernameField().setText("smoke");
            login.getPasswordField().setText("test-only");
            login.getLoginButton().fire();
            if (!stage.getTitle().equals("Echo Shift - Player Home")) throw new AssertionError("Player login");
            stage.close();
            System.out.println("PASS: JavaFX menu, gameplay, save and return navigation");
            Platform.exit();
        } catch (Throwable error) {
            error.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) { launch(args); }
}
