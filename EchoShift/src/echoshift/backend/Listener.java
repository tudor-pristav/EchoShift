package echoshift.backend;

public class Listener extends Entity{
    private double frozenRemaining;

    public Listener(){
        frozenRemaining = 0.0;
    }

    public boolean attemptMove(){

    }

    public boolean isFrozen() {
        return frozenRemaining > 0;
    }

    public void freeze(int duration) {
        frozenRemaining = duration;
    }
}
