package io.github.wand555.challengesreworkedapi.goals.mob;

import io.github.wand555.challengesreworkedapi.goals.Goal;
import io.github.wand555.challengesreworkedapi.goals.GoalCommon;

public interface MobGoal extends Goal {

    @Override
    MobGoalCommon getCommon();
}
