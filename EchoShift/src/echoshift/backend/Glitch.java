// Glitch.java
package echoshift.backend;

/**
 * Glitch effect applied by a Listener to any SystemDevice.
 * Disables the device until reset or glitch timer expires.
 */
public class Glitch {

    public Glitch(SystemDevice target, double duration) {
        if (target != null && target.isFunctional()) {
            target.applyGlitch(duration);
        }
    }

    /**
     * Apply glitch to a SystemDevice target
     * @param target the device to be glitched
     */
    public void applyToDevice(SystemDevice target, double duration) {
        if (target != null && target.isFunctional()) {
            target.applyGlitch(duration);
        }
    }
}