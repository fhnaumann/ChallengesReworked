package wand555.github.io.challengesreworked.challenges.nodamage;

import wand555.github.io.challengesreworked.challenges.Challenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishment;

import java.util.Collection;
import java.util.List;

public interface NoDamageChallenge extends PunishableChallenge {

    @Override
    NoDamageChallengeCommon getCommon();

    @Override
    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of(HealthPunishment.class);
    }
}
