package wand555.github.io.challengesreworked;

import io.github.wand555.challengesreworkedapi.Common;
import io.github.wand555.challengesreworkedapi.Commonable;
import io.github.wand555.challengesreworkedapi.challenges.Challenge;
import io.github.wand555.challengesreworkedapi.challenges.ChallengeCommon;
import io.github.wand555.challengesreworkedapi.challenges.nocrafting.NoCraftingChallenge;
import io.github.wand555.challengesreworkedapi.challenges.nocrafting.NoCraftingChallengeCommon;
import io.github.wand555.challengesreworkedapi.goals.Goal;
import io.github.wand555.challengesreworkedapi.goals.GoalCommon;
import io.github.wand555.challengesreworkedapi.goals.itemcollect.ItemCollectGoal;
import io.github.wand555.challengesreworkedapi.goals.itemcollect.ItemCollectGoalCommon;
import io.github.wand555.challengesreworkedapi.goals.mob.MobGoal;
import io.github.wand555.challengesreworkedapi.goals.mob.MobGoalCommon;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;
import io.github.wand555.challengesreworkedapi.punishments.health.HealthPunishment;
import io.github.wand555.challengesreworkedapi.punishments.health.HealthPunishmentCommon;
import io.github.wand555.challengesreworkedapi.punishments.randomitem.RandomItemPunishmentCommon;
import wand555.github.io.challengesreworked.goal.PluginItemCollectGoal;
import wand555.github.io.challengesreworked.goal.PluginMobGoal;
import wand555.github.io.challengesreworked.punishment.PluginHealthPunishment;
import wand555.github.io.challengesreworked.punishment.PluginRandomItemPunishment;

public class Wrapper {

    public static Commonable wrap(Common common) {
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
