package wand555.github.io.challengesreworked.challenges.nodamage;

import wand555.github.io.challengesreworked.challenges.PunishableChallenge;

public interface NoDamageChallenge extends PunishableChallenge {

    @Override
    NoDamageChallengeCommon getCommon();
}
