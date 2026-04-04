// SystemDevice.java
package echoshift.backend;

/**
 * Base for player-usable tools (AudioDevice, ScanningDevice).
 */
public abstract class SystemDevice{
    protected GameMap gameMap;
    protected int currentDifficulty;
    protected double durability;
    protected final double maxDurability;
    protected boolean isGlitched = false;
    protected int locationId;

    protected SystemDevice(GameMap gameMap, int startingLocationId, double maxDurability) {
        this.currentDifficulty = 1;
        this.maxDurability = Math.max(10, maxDurability);
        this.durability = this.maxDurability;
        this.locationId = startingLocationId < 0 ? startingLocationId : 1;
    }

    public void setDiff(int diff) {
        if (diff < 1) diff = 1;
        this.currentDifficulty = diff;
    }

    /**
     * Wear multiplier for SystemDevices.
     */
    public double getWearMultiplier() {
        return 0.2 * currentDifficulty;
    }

    public void degrade() {
        if (!isFunctional()) return;
        double wear = getWearMultiplier();
        durability = Math.max(0, durability - (int) Math.ceil(wear));
    }

    public void resetDevice() {
        durability = maxDurability;
        isGlitched = false;
    }

    public double getDurabilityPercentage() {
        return (maxDurability > 0) ? (durability * 100.0 / maxDurability) : 0.0;
    }

    public boolean isFunctional() {
        return durability > 0 && !isGlitched;
    }

    public void applyGlitch(double duration) {
        isGlitched = true;
    }

    public int getCurrentRoomId() {
        return locationId;
    }

    public void setLocation(int roomId) {
        this.locationId = roomId < 0 ? roomId : 1;
    }
}