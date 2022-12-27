package wand555.github.io.challengesreworked.goals;

import wand555.github.io.challengesreworked.Commonable;

public interface Goal extends Commonable {

    boolean isComplete();

    void setComplete(boolean complete);

    @Override
    GoalCommon getCommon();
}
