package io.github.wand555.challengesreworkedapi.goals;

import io.github.wand555.challengesreworkedapi.Common;
import io.github.wand555.challengesreworkedapi.Commonable;
import io.github.wand555.challengesreworkedapi.Storable;

public interface Goal extends Commonable, Storable {

    boolean isComplete();

    void setComplete(boolean complete);

    @Override
    GoalCommon getCommon();
}
