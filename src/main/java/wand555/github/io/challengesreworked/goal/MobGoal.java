package wand555.github.io.challengesreworked.goal;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class MobGoal extends Goal implements Listener {

    private final EntityType toKill;
    private Collect collect;

    public MobGoal(EntityType toKill, int amountNeeded) {
        this(false, toKill, new Collect(amountNeeded, 0));
    }

    public MobGoal(boolean complete, EntityType toKill, Collect collect) {
        super(complete);
        this.toKill = toKill;
        this.collect = collect;
        JavaPlugin plugin = ChallengeManager.getInstance().getPlugin();
        if(HandlerList.getRegisteredListeners(plugin).stream().noneMatch(registeredListener -> registeredListener.getListener().getClass() == this.getClass())) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
        StandardSerializer.getDefault().register(MobGoal.class, adapter);
    }

    @Override
    public void onGoalReached() {
        ChallengeManager manager = ChallengeManager.getInstance();
        setComplete(true);
        ChatLogger.log("goals.mobgoal.complete", Map.of(
                PlaceHolderHandler.AMOUNT_PLACEHOLDER, collect.getAmountNeeded(),
                PlaceHolderHandler.MOB_PLACEHOLDER, toKill
        ));
        if(manager.allGoalsComplete()) {
            manager.end(ChallengeEnding.SUCCESS);
        }
    }

    @EventHandler
    private void mobDeath(EntityDeathEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        if(event.getEntityType() != toKill) {
            return;
        }
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }
        collect.setCurrentAmount(collect.getCurrentAmount()+1);
        ChatLogger.log("goals.mobgoal.success", Map.of(
                PlaceHolderHandler.PLAYER_PLACEHOLDER, killer,
                PlaceHolderHandler.MOB_PLACEHOLDER, event.getEntityType(),
                PlaceHolderHandler.CURRENT_AMOUNT_PLACEHOLDER, collect.getCurrentAmount(),
                PlaceHolderHandler.AMOUNT_NEEDED_PLACEHOLDER, collect.getAmountNeeded()
        ));
        if(collect.reached()) {
            onGoalReached();
        }
    }

    private final TypeAdapter<MobGoal> adapter = new TypeAdapter<>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull MobGoal mobGoal) {
            return Map.of(
                    "complete", isComplete(),
                    "toKill", toKill.toString(),
                    "collect", collect
            );
        }

        @NotNull
        @Override
        public MobGoal deserialize(@NotNull Map<Object, Object> map) {
            return new MobGoal((boolean) map.get("complete"), EntityType.valueOf(map.get("toKill").toString()), (Collect) map.get("collect"));
        }
    };
}
