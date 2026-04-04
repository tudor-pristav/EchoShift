package echoshift.backend;

/**
 * ScanningDevice reveals Listener location when enabled.
 * Listener can glitch it. Max durability passed from NightDifficulty schedule.
 */
public class ScanningDevice extends SystemDevice {

    public ScanningDevice(GameMap gameMap, int startingRoomID, double maxDurability) {
        super(gameMap, startingRoomID, maxDurability);   // default location = office
    }

    /**
     * Scan the target listener. Called after successful typing of scan word.
     * Returns the current room ID (String) so the JavaFX UI can highlight it on the map.
     * @param target The target Listener to be scanned
     */
    public int scanFor(Listener target) {
        if (target == null || !isFunctional()) return -1;

        int locationId = target.getCurrentRoomId();
        // TODO: Make Listener visible in JavaFX
        return locationId;
    }
}