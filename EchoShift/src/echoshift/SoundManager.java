package echoshift;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * SoundManager controls playing and stopping sound by
 * utilizing SoundEffectType.
 * <p>
 * It supports MP3 and WAV files using JavaFX MediaPlayer
 * and Java Sound API, respectively.
 * </p>
 * @author Matthew Taylor
 */
public class SoundManager {

    /** Boolean indicating whether the sound manager is muted.*/
    private boolean isMuted = false;

    /** A map holding preloaded sound clips keyed by their SoundEffectType.*/
    private final Map<SoundEffectType, Clip> soundClips = new EnumMap<>(SoundEffectType.class);

    /**
     * SoundManager constructor. Initializes and
     * loads all sound clips directly into the instance,
     * making an efficient system.
     */
    public SoundManager() {
        loadSounds();
    }

    /**
     * Loads sound clips for quick playback.
     */
    private void loadSounds() {
        for (SoundEffectType effect : SoundEffectType.values()) {
            String ext = effect.getFileExtension();
            try {
                if ("wav".equals(ext)) {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/" + effect.getFilename()));
                    clip.open(ais);
                    soundClips.put(effect, clip);
                } else {
                    System.err.println("Unsupported sound format for effect: " + effect.name() + " (" + ext + ")");
                    // Optionally handle other formats or skip
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Failed to load sound: " + effect.getFilename());
                e.printStackTrace();
            }
        }
    }

    /**
     * Play sound effect by SoundEffectType.
     *
     * @param effect the SoundEffectType representing the sound effect to play
     */
    public void playSound(SoundEffectType effect) {
        if (isMuted) return;

        Clip clip = soundClips.get(effect);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Sound effect not loaded: " + effect.name());
        }
    }

    /**
     * Stops the current sound effect that is playing.
     *
     * @param effect the SoundEffectType representing the sound effect to stop.
     */
    public void stopSound(SoundEffectType effect) {
        Clip clip = soundClips.get(effect);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Check to see if sound manager is muted.
     *
     * @return true if muted, false if not muted.
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Sets state of sound (muted or not).
     * When true, any sound that is playing is stopped.
     *
     * @param muted playback allowed when set to false.
     */
    public void setMuted(boolean muted) {
        this.isMuted = muted;
        if (muted) {
            soundClips.values().forEach(clip -> {
                if (clip.isRunning()) clip.stop();
            });
        }
    }
}
