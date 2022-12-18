package wand555.github.io.challengesreworked.goal;

public abstract class Goal {

    private boolean complete;

    protected Goal(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    protected void setComplete(boolean complete) {
        this.complete = complete;
    }

    public abstract void onGoalReached();
}
