package echoshift.animations;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Utility class for playing UI sound effects and background music.
 */
public class SoundEffects {

    private static final String CLICK_SOUND_PATH = "/echoshift/sounds/click_004.mp3";
    private static final String MUSIC_PATH = "/echoshift/sounds/background.mp3";

    private static final AudioClip CLICK_SOUND;
    private static MediaPlayer mediaPlayer;
    private static boolean initialized = false;

    /**
     * Master volume for all game audio.
     * Range: 0.0 to 1.0
     */
    private static double masterVolume = 1.0;

    static {
        URL soundUrl = SoundEffects.class.getResource(CLICK_SOUND_PATH);

        if (soundUrl == null) {
            throw new IllegalStateException("Sound not found: " + CLICK_SOUND_PATH);
        }

        CLICK_SOUND = new AudioClip(soundUrl.toExternalForm());
        CLICK_SOUND.setVolume(masterVolume);
    }

    /**
     * Plays the button click sound once.
     */
    public static void playClickSound() {
        CLICK_SOUND.play();
    }

    /**
     * Starts the background music if it is not already playing.
     * Safe to call multiple times.
     */
    public static void play() {
        if (!initialized) {
            initializePlayer();
        }

        if (mediaPlayer != null &&
                mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    /**
     * Pauses the background music.
     */
    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Stops the background music and resets it to the beginning.
     */
    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Sets the master audio volume for both music and sound effects.
     *
     * @param volume volume between 0.0 and 1.0
     */
    public static void setVolume(double volume) {
        masterVolume = Math.max(0.0, Math.min(1.0, volume));

        CLICK_SOUND.setVolume(masterVolume);

        if (mediaPlayer != null) {
            mediaPlayer.setVolume(masterVolume);
        }
    }

    /**
     * Returns the current master volume as a value between 0.0 and 1.0.
     *
     * @return current volume
     */
    public static double getVolume() {
        return masterVolume;
    }

    /**
     * Creates the shared MediaPlayer once.
     */
    private static void initializePlayer() {
        URL musicUrl = SoundEffects.class.getResource(MUSIC_PATH);

        if (musicUrl == null) {
            System.out.println("Background music file not found: " + MUSIC_PATH);
            return;
        }

        Media media = new Media(musicUrl.toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(masterVolume);

        initialized = true;
    }
}