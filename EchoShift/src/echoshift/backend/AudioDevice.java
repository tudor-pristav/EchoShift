package echoshift.backend;

/**
 * AudioDevice lures the Listener to its location.
 * Listener can glitch it. Max durability passed from NightDifficulty schedule.
 */
public class AudioDevice extends SystemDevice {

    public AudioDevice(GameMap gameMap, int startingRoomID, int maxDurability) {
        super(gameMap, startingRoomID, maxDurability);
    }

    /**
     * When player types the lure word, Listener targets the AudioDevice instead of the player
     * @param target The target Listener to be lured.
     */
    public void lure(Listener target) {
        if (target == null || !isFunctional()) return;
        target.lureTo(getCurrentRoomId());
        target.attemptMove();
    }
}