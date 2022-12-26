package wand555.github.io.challengesreworked.challenges;

import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.Collection;

public interface Punishable {

    public Collection<PunishmentCommon> getPunishmentsCommon();
    public void setPunishmentsCommon(Collection<PunishmentCommon> punishmentsCommon);
}
