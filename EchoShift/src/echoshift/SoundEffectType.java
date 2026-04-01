package echoshift;

/**
 * Contains the required sound effect files (in MP3 or WAV)
 * as enumerations and is utilized by SoundManager class.
 *
 * @author Matthew Taylor
 */
public enum SoundEffectType {

    // The enums below still need sound effects:
    LIFE_LOST("life_lost.wav"),
    GAME_OVER("game_over.wav"),
    POWERUP_USED("powerup_used.wav"),
    SYSTEM_RESET("system_reset.wav"),
    LURE_ACTIVATED("lure_activated.wav"),
    SCAN_ACTIVATED("scan_activated.wav"),
    LISTENER_FROZEN("listener_frozen.wav"),
    LISTENER_MOVE("listener_move.wav"),
    ACHIEVEMENT_UNLOCKED("achievement_unlocked.wav"),
    BUTTON_CLICK("button_click.wav"),
    MENU_OPEN("menu_open.wav"),
    MENU_CLOSE("menu_close.wav"),
    ITEM_PURCHASED("item_purchased.wav"),
    ERROR_SOUND("error_sound.wav"),
    CUSTOM_NIGHT_UNLOCKED("custom_night_unlocked.wav"),
    NIGHT_STARTED("night_started.wav"),
    SAVE_SUCCESS("save_success.wav"),
    SAVE_FAILURE("save_failure.wav");

    /** Filename of music track */
    private final String filename;

    /**
     * SoundEffectType constructor. Initializes filename.
     *
     * @param filename the name of the sound effect track.
     */
    SoundEffectType(String filename) {
        this.filename = filename;
    }

    /**
     * Retrieves the filename.
     *
     * @return filename as a String.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Gets the extension of file (either mp3 or wav) and
     * returns it. Returns "" for a failed get mission.
     *
     * @return extension of file.
     */
    public String getFileExtension() {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
