package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import wand555.github.io.challengesreworked.challenges.noblockbreaking.NoBlockBreakingChallengeCommon;
import wand555.github.io.challengesreworked.challenges.noblockplacing.NoBlockPlacingChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nodamage.NoDamageChallengeCommon;
import wand555.github.io.challengesreworked.challenges.noregeneration.NoRegenerationChallengeCommon;
import wand555.github.io.challengesreworked.challenges.randomdrops.RandomDropsChallengeCommon;
import wand555.github.io.challengesreworked.goals.itemcollect.ItemCollectGoalCommon;
import wand555.github.io.challengesreworked.goals.mob.MobGoalCommon;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomeffect.RandomEffectPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;

import java.util.List;

public final class ChallengesReworkedApi extends JavaPlugin {

    /**
     * Needs to be called once at the start of the application
     * before anything is stored/loaded from a file.
     */
    public static void registerTypeAdapters() {
        StandardSerializer.getDefault().register(Collect.class, Collect.adapter);

        registerChallengeTypeAdapters();
        registerGoalTypeAdapters();
        registerPunishmentTypeAdapters();

    }

    private static void registerChallengeTypeAdapters() {
        StandardSerializer.getDefault().register(NoBlockBreakingChallengeCommon.class, NoBlockBreakingChallengeCommon.adapter);
        StandardSerializer.getDefault().register(NoBlockPlacingChallengeCommon.class, NoBlockPlacingChallengeCommon.adapter);
        StandardSerializer.getDefault().register(NoCraftingChallengeCommon.class, NoCraftingChallengeCommon.adapter);
        StandardSerializer.getDefault().register(NoDamageChallengeCommon.class, NoDamageChallengeCommon.adapter);
        StandardSerializer.getDefault().register(RandomDropsChallengeCommon.class, RandomDropsChallengeCommon.adapter);
        StandardSerializer.getDefault().register(NoRegenerationChallengeCommon.class, NoRegenerationChallengeCommon.adapter);
    }

    private static void registerGoalTypeAdapters() {
        StandardSerializer.getDefault().register(ItemCollectGoalCommon.class, ItemCollectGoalCommon.adapter);
        StandardSerializer.getDefault().register(MobGoalCommon.class, MobGoalCommon.adapter);
    }

    private static void registerPunishmentTypeAdapters() {
        StandardSerializer.getDefault().register(HealthPunishmentCommon.class, HealthPunishmentCommon.adapter);
        StandardSerializer.getDefault().register(RandomEffectPunishmentCommon.class, RandomEffectPunishmentCommon.adapter);
        StandardSerializer.getDefault().register(RandomItemPunishmentCommon.class, RandomItemPunishmentCommon.adapter);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
