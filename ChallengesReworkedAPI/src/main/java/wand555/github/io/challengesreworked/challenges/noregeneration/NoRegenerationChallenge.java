package wand555.github.io.challengesreworked.challenges.noregeneration;

import wand555.github.io.challengesreworked.challenges.Challenge;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;

public interface NoRegenerationChallenge extends Challenge {

    @Override
    NoRegenerationChallengeCommon getCommon();
}
