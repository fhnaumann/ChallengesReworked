package wand555.github.io.challengesreworked.goals;

import wand555.github.io.challengesreworked.Commonable;
import wand555.github.io.challengesreworked.Storable;

public interface Goal extends Commonable, Storable {

    boolean isComplete();

    void setComplete(boolean complete);

    @Override
    GoalCommon getCommon();
}
