package io.github.wand555.challengesreworkedapi.challenges;

import io.github.wand555.challengesreworkedapi.punishments.Punishment;

import java.util.Collection;

public interface PunishableChallenge extends Challenge, Punishable {

    @Override
    PunishableChallengeCommon getCommon();

    @Override
    Collection<? extends Punishment> getPunishments();

    @Override
    void setPunishments(Collection<? extends Punishment> punishments);
}
