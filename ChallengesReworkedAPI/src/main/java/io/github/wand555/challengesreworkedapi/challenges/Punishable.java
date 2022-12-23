package io.github.wand555.challengesreworkedapi.challenges;

import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;

import java.util.Collection;

public interface Punishable {

    public Collection<PunishmentCommon> getPunishmentsCommon();
    public void setPunishmentsCommon(Collection<PunishmentCommon> punishmentsCommon);
}
