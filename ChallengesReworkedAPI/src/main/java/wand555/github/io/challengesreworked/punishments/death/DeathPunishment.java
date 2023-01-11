package wand555.github.io.challengesreworked.punishments.death;

import wand555.github.io.challengesreworked.challenges.Challenge;
import wand555.github.io.challengesreworked.challenges.nodeath.NoDeathChallenge;
import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.endchallenge.EndChallengePunishment;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishment;

import java.util.Collection;
import java.util.List;

public interface DeathPunishment extends Punishment {

    @Override
    DeathPunishmentCommon getCommon();

    @Override
    default Collection<Class<? extends Challenge>> getIncompatibleChallenges() {
        return List.of(
                NoDeathChallenge.class
        );
    }

    @Override
    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of(
                EndChallengePunishment.class,
                HealthPunishment.class
        );
    }
}
