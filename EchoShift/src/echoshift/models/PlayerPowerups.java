package echoshift.models;

/**
 * Stores the number of each powerup owned by a player.
 */
public class PlayerPowerups {
    private int easyWords;
    private int extraLife;
    private int instantLure;
    private int instantRepair;

    public PlayerPowerups() {
        this.easyWords = 0;
        this.extraLife = 0;
        this.instantLure = 0;
        this.instantRepair = 0;
    }

    public PlayerPowerups(int easyWords, int extraLife, int instantLure, int instantRepair) {
        this.easyWords = easyWords;
        this.extraLife = extraLife;
        this.instantLure = instantLure;
        this.instantRepair = instantRepair;
    }

    public int getEasyWords() {
        return easyWords;
    }

    public void setEasyWords(int easyWords) {
        this.easyWords = easyWords;
    }

    public int getExtraLife() {
        return extraLife;
    }

    public void setExtraLife(int extraLife) {
        this.extraLife = extraLife;
    }

    public int getInstantLure() {
        return instantLure;
    }

    public void setInstantLure(int instantLure) {
        this.instantLure = instantLure;
    }

    public int getInstantRepair() {
        return instantRepair;
    }

    public void setInstantRepair(int instantRepair) {
        this.instantRepair = instantRepair;
    }
}