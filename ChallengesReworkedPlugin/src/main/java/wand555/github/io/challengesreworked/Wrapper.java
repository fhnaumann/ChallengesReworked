package wand555.github.io.challengesreworked;

import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import wand555.github.io.challengesreworked.goals.Goal;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import wand555.github.io.challengesreworked.goals.itemcollect.ItemCollectGoalCommon;
import wand555.github.io.challengesreworked.goals.mob.MobGoalCommon;
import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;
import wand555.github.io.challengesreworked.challenges.PluginChallenge;
import wand555.github.io.challengesreworked.challenges.nocrafting.PluginNoCraftingChallenge;
import wand555.github.io.challengesreworked.goals.itemcollect.PluginItemCollectGoal;
import wand555.github.io.challengesreworked.goals.mob.PluginMobGoal;
import wand555.github.io.challengesreworked.punishments.health.PluginHealthPunishment;
import wand555.github.io.challengesreworked.punishments.randomitem.PluginRandomItemPunishment;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Wrapper {

    public static Commonable wrap(Common common) {

        //io.github.wand555.challengesreworkedapi.[challenges/goals/punishments].[<<challenge>>/<<goal>>/<<punishment>>].[<<challenge>>/<<goal>>/<<punishment>>]Common
        String commonClassName = common.getClass().getName();
        String[] split = commonClassName.split("\\.");
        System.out.println(Arrays.toString(split));
        split[split.length-1] = "Plugin" + split[split.length-1].replace("Common", "");
        System.out.println(Arrays.toString(split));
        String pluginClassName = String.join(".", split);
        try {
            Class<?> pluginClazz = Class.forName(pluginClassName);
            return (Commonable) pluginClazz.getConstructor(common.getClass()).newInstance(common);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        /*

        if(common instanceof ChallengeCommon challengeCommon) {
            return wrapChallengeCommon(challengeCommon);
        }
        if(common instanceof GoalCommon goalCommon) {
            return wrapGoal(goalCommon);
        }
        if(common instanceof PunishmentCommon punishmentCommon) {
            return wrapPunishment(punishmentCommon);
        }
        throw new IllegalArgumentException("%s cannot be parsed to its container class!".formatted(common));

         */
    }

    private static <T extends ChallengeCommon> PluginChallenge wrapChallengeCommon(T common) {
        if(common instanceof NoCraftingChallengeCommon noCraftingChallengeCommon) {
            return new PluginNoCraftingChallenge(noCraftingChallengeCommon);
        }
        throw new IllegalArgumentException("%s cannot be parsed to its container class!".formatted(common));
    }

    private static <T extends GoalCommon> Goal wrapGoal(T common) {
        if(common instanceof MobGoalCommon mobGoalCommon) {
            return new PluginMobGoal(mobGoalCommon);
        }
        if(common instanceof ItemCollectGoalCommon itemCollectGoalCommon) {
            return new PluginItemCollectGoal(itemCollectGoalCommon);
        }
        throw new IllegalArgumentException("%s cannot be parsed to its container class!".formatted(common));
    }

    public static <T extends PunishmentCommon> Punishment wrapPunishment(T common) {
        if(common instanceof HealthPunishmentCommon healthPunishmentCommon) {
            return new PluginHealthPunishment(healthPunishmentCommon);
        }
        if(common instanceof RandomItemPunishmentCommon randomItemPunishmentCommon) {
            return new PluginRandomItemPunishment(randomItemPunishmentCommon);
        }
        throw new IllegalArgumentException("%s cannot be parsed to its container class!".formatted(common));
    }
}
