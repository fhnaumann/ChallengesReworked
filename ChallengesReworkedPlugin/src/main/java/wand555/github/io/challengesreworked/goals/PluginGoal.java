package wand555.github.io.challengesreworked.goals;

import org.bukkit.plugin.java.JavaPlugin;
import wand555.github.io.challengesreworked.ChallengesReworked;

public abstract class PluginGoal implements Goal {

    private final GoalCommon common;

    protected final ChallengesReworked plugin;

    protected PluginGoal(GoalCommon common) {
        this.common = common;
        this.plugin = JavaPlugin.getPlugin(ChallengesReworked.class);
    }

    @Override
    public GoalCommon getCommon() {
        return common;
    }

    public abstract void onGoalReached();

    public abstract boolean isGoalReached();
}
