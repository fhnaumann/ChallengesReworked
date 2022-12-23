package io.github.wand555.challengesreworkedapi.challenges;

import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;

import java.util.Collection;

public interface PunishableChallenge extends Challenge, Punishable {

    @Override
    PunishableChallengeCommon getCommon();

    /**
     * PunishableChallengeCommon holds a collection of PunishmentCommon.
     * These common objects need to be cast to an actual Punishment and because
     * the api module does not define a Punishment implementation, this is left
     * to the implementing module.
     * So in a different module the PunishmentCommon will be wrapped into Punishments.
     * As a side note:
     * PunishableChallenge#getPunishment()[0-n]#getCommon() is the same as Common#getCommon()#getPunishmentCommons()[0-n]
     * @return
     */
    Collection<? extends Punishment> mapToPunishments();

    Collection<? extends Punishment> getPunishments();

    void setPunishments(Collection<? extends Punishment> punishments);
    @Override
    default Collection<PunishmentCommon> getPunishmentsCommon() {
        return getCommon().getPunishmentCommons();
    }

    @Override
    default void setPunishmentsCommon(Collection<PunishmentCommon> punishmentsCommon) {
        getCommon().setPunishmentCommons(punishmentsCommon);
    }

}
