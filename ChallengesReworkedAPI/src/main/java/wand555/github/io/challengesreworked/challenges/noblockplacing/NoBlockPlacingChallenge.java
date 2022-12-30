package wand555.github.io.challengesreworked.challenges.noblockplacing;

import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;

public interface NoBlockPlacingChallenge extends PunishableChallenge {

    @Override
    NoBlockPlacingChallengeCommon getCommon();
}
