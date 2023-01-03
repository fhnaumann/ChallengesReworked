package wand555.github.io.challengesreworked;

import wand555.github.io.challengesreworked.challenges.Challenge;
import wand555.github.io.challengesreworked.goals.Goal;
import wand555.github.io.challengesreworked.punishments.Punishment;

import java.util.Collection;
import java.util.List;

public interface Commonable {
    Common getCommon();

    default Collection<Class<? extends Challenge>> getIncompatibleChallenges() {
        return List.of();
    }

    default boolean isIncompatibleWithChallenge(Class<? extends Challenge> clazz) {
        return getIncompatibleChallenges().contains(clazz);
    }

    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of();
    }

    default boolean isIncompatibleWithPunishment(Class<? extends Punishment> clazz) {
        return getIncompatiblePunishments().contains(clazz);
    }

    default Collection<Class<? extends Goal>> getIncompatibleGoals() {
        return List.of();
    }

    default boolean isIncompatibleWithGoal(Class<? extends Goal> clazz) {
        return getIncompatibleGoals().contains(clazz);
    }
}
