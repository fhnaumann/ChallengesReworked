package wand555.github.io.challengesreworked.goals;

import io.github.wand555.challengesreworkedapi.goals.Goal;
import io.github.wand555.challengesreworkedapi.goals.GoalCommon;
import org.bukkit.plugin.java.JavaPlugin;
import wand555.github.io.challengesreworked.ChallengesReworked;

public abstract class PluginGoal implements Goal {

    private final GoalCommon common;

    protected final ChallengesReworked plugin;

    protected PluginGoal(GoalCommon common) {
        this.common = common;
        this.plugin = JavaPlugin.getPlugin(ChallengesReworked.class);
        register();
    }

    @Override
    public GoalCommon getCommon() {
        return common;
    }

    @Override
    public boolean isComplete() {
        return common.isComplete();
    }

    @Override
    public void setComplete(boolean complete) {
        common.setComplete(complete);
    }

    public abstract void onGoalReached();

    public abstract boolean isGoalReached();
}
