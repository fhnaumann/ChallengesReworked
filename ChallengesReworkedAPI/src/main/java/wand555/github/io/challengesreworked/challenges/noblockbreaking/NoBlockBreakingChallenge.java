package wand555.github.io.challengesreworked.challenges.noblockbreaking;

import wand555.github.io.challengesreworked.challenges.PunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;

public interface NoBlockBreakingChallenge extends PunishableChallenge {

    @Override
    NoBlockBreakingChallengeCommon getCommon();
}
