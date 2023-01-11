package wand555.github.io.challengesreworked.challenges.nodeath;

import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.death.DeathPunishment;

import java.util.Collection;
import java.util.List;

public interface NoDeathChallenge extends PunishableChallenge {

    @Override
    NoDeathChallengeCommon getCommon();

    @Override
    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of(
                DeathPunishment.class
        );
    }
}
