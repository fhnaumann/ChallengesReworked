package wand555.github.io.challengesreworked.goals;

import wand555.github.io.challengesreworked.Common;

public abstract class GoalCommon implements Common {

    private boolean complete;

    protected GoalCommon(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
