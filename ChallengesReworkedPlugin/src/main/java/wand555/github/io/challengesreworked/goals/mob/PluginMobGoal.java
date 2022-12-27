package wand555.github.io.challengesreworked.goals.mob;

import wand555.github.io.challengesreworked.Collect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.goals.ChallengeEnding;
import wand555.github.io.challengesreworked.goals.PluginGoal;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class PluginMobGoal extends PluginGoal implements MobGoal, Listener {


    public PluginMobGoal(MobGoalCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @Override
    public void onGoalReached() {
        if(isComplete()) {
            return;
        }
        ChallengeManager manager = ChallengeManager.getInstance();
        setComplete(true);
        ChatLogger.log("goals.mobgoal.complete", Map.of(
                PlaceHolderHandler.AMOUNT_PLACEHOLDER, getCommon().getToKill().keySet().size(),
                PlaceHolderHandler.MOB_PLACEHOLDER, getCommon().getToKill()
        ));
        if(manager.allGoalsComplete()) {
            manager.end(ChallengeEnding.SUCCESS);
        }
    }

    @Override
    public boolean isGoalReached() {
        return getCommon().getToKill().values().stream().allMatch(Collect::isReached);
    }

    @EventHandler
    private void mobDeath(EntityDeathEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }
        newMobKilled(killer, event.getEntityType());
    }

    private void newMobKilled(Player killer, EntityType killed) {
        getCommon().getToKill().computeIfPresent(killed, (entityType, collect) -> {
            collect.setCurrentAmount(collect.getCurrentAmount()+1);
            ChatLogger.log("goals.mobgoal.success", Map.of(
                    PlaceHolderHandler.PLAYER_PLACEHOLDER, killer,
                    PlaceHolderHandler.MOB_PLACEHOLDER, killed,
                    PlaceHolderHandler.CURRENT_AMOUNT_PLACEHOLDER, collect.getCurrentAmount(),
                    PlaceHolderHandler.AMOUNT_NEEDED_PLACEHOLDER, collect.getAmountNeeded()
            ));
            return collect;
        });
        if(isGoalReached()) {
            onGoalReached();
        }

    }

    @Override
    public MobGoalCommon getCommon() {
        return (MobGoalCommon) super.getCommon();
    }
}
