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
import java.util.stream.Collectors;

public class MobGoal extends Goal implements Listener {

    private final Map<EntityType, Collect> toKill;

    public MobGoal(Map<EntityType, Collect> toKill) {
        this(false, toKill);
    }

    public MobGoal(boolean complete, Map<EntityType, Collect> toKill) {
        super(complete);
        this.toKill = toKill;
        JavaPlugin plugin = ChallengeManager.getInstance().getPlugin();
        if(HandlerList.getRegisteredListeners(plugin).stream().noneMatch(registeredListener -> registeredListener.getListener().getClass() == this.getClass())) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
        StandardSerializer.getDefault().register(MobGoal.class, adapter);
    }

    @Override
    public void onGoalReached() {
        if(isComplete()) {
            return;
        }
        ChallengeManager manager = ChallengeManager.getInstance();
        setComplete(true);
        ChatLogger.log("goals.mobgoal.complete", Map.of(
                PlaceHolderHandler.AMOUNT_PLACEHOLDER, toKill.keySet().size(),
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
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }
        newMobKilled(killer, event.getEntityType());
    }

    private void newMobKilled(Player killer, EntityType killed) {
        toKill.computeIfPresent(killed, (entityType, collect) -> {
            collect.setCurrentAmount(collect.getCurrentAmount()+1);
            ChatLogger.log("goals.mobgoal.success", Map.of(
                    PlaceHolderHandler.PLAYER_PLACEHOLDER, killer,
                    PlaceHolderHandler.MOB_PLACEHOLDER, killed,
                    PlaceHolderHandler.CURRENT_AMOUNT_PLACEHOLDER, collect.getCurrentAmount(),
                    PlaceHolderHandler.AMOUNT_NEEDED_PLACEHOLDER, collect.getAmountNeeded()
            ));
            return collect;
        });
        checkGoalReached();

    }

    private void checkGoalReached() {
        if(toKill.values().stream().allMatch(Collect::reached)) {
            onGoalReached();
        }
    }

    private final TypeAdapter<MobGoal> adapter = new TypeAdapter<>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull MobGoal mobGoal) {
            return Map.of(
                    "complete", isComplete(),
                    "toKill", toKill.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue))
            );
        }

        @NotNull
        @Override
        public MobGoal deserialize(@NotNull Map<Object, Object> map) {
            return new MobGoal(
                    (boolean) map.get("complete"),
                    ((Map<?, ?>) map.get("toKill")).entrySet().stream().collect(Collectors.toMap(
                            entry -> EntityType.valueOf(entry.getKey().toString()), entry -> (Collect) entry.getValue())
                    ));
        }
    };
}
