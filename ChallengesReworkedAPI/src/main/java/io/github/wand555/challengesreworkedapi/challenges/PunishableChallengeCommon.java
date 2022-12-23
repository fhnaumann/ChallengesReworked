package io.github.wand555.challengesreworkedapi.challenges;

import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;

import java.util.Collection;
import java.util.Set;

public abstract class PunishableChallengeCommon extends ChallengeCommon {

    protected Collection<PunishmentCommon> punishmentCommons;

    public PunishableChallengeCommon() {
        this(Set.of());
    }

    public PunishableChallengeCommon(Collection<PunishmentCommon> punishmentCommons) {
        super();
        this.punishmentCommons = punishmentCommons;
    }

    public Collection<PunishmentCommon> getPunishmentCommons() {
        return punishmentCommons;
    }

    public void setPunishmentCommons(Collection<PunishmentCommon> punishmentCommons) {
        this.punishmentCommons = punishmentCommons;
    }
}
