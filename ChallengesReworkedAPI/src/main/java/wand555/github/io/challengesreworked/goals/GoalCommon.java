package wand555.github.io.challengesreworked.goals;

import wand555.github.io.challengesreworked.Common;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalCommon that = (GoalCommon) o;
        return complete == that.complete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(complete);
    }
}
