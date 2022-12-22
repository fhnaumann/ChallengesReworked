package wand555.github.io.challengesreworked.goal;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import io.github.wand555.challengesreworkedapi.Collect;
import io.github.wand555.challengesreworkedapi.goals.GoalCommon;
import io.github.wand555.challengesreworkedapi.goals.mob.MobGoal;
import io.github.wand555.challengesreworkedapi.goals.mob.MobGoalCommon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public void register() {
        StandardSerializer.getDefault().register(PluginMobGoal.class, new TypeAdapter<PluginMobGoal>() {
            @NotNull
            @Override
            public Map<Object, Object> serialize(@NotNull PluginMobGoal pluginMobGoal) {
                return getCommon().getTypeAdapter().serialize(pluginMobGoal.getCommon());
            }

            @NotNull
            @Override
            public PluginMobGoal deserialize(@NotNull Map<Object, Object> map) {
                return new PluginMobGoal(getCommon().getTypeAdapter().deserialize(map));
            }
        });
    }
}
