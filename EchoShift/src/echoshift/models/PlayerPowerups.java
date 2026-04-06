package echoshift.models;

/**
 * Stores counts of powerups owned by a player.
 *
 * @author Tudor Mihai Pristav
 */
public class PlayerPowerups {
    private int easyWords;
    private int extraLife;
    private int instantLure;
    private int instantRepair;

    /**
     * Initializes powerup counts.
     *
     * @param ew easy words count
     * @param el extra life count
     * @param il instant lure count
     */
    public PlayerPowerups(int ew, int el, int il) {
        this.easyWords = ew;
        this.extraLife = el;
        this.instantLure = il;
    }

    /**
     * @return number of easy words powerups
     */
    public int getEasyWords() {
        return easyWords;
    }

    /**
     * @param easyWords new easy words count
     */
    public void setEasyWords(int easyWords) {
        this.easyWords = easyWords;
    }

    /**
     * @return number of extra life powerups
     */
    public int getExtraLife() {
        return extraLife;
    }

    /**
     * @param extraLife new extra life count
     */
    public void setExtraLife(int extraLife) {
        this.extraLife = extraLife;
    }

    /**
     * @return number of instant lure powerups
     */
    public int getInstantLure() {
        return instantLure;
    }

    /**
     * @param instantLure new instant lure count
     */
    public void setInstantLure(int instantLure) {
        this.instantLure = instantLure;
    }

    /**
     * @return number of instant repair powerups
     */
    public int getInstantRepair() {
        return instantRepair;
    }

    /**
     * @param instantRepair new instant repair count
     */
    public void setInstantRepair(int instantRepair) {
        this.instantRepair = instantRepair;
    }
}