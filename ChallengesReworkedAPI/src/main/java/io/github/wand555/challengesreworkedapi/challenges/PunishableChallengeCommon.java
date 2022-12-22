package io.github.wand555.challengesreworkedapi.challenges;

import io.github.wand555.challengesreworkedapi.punishments.Punishment;

import java.util.Collection;
import java.util.Set;

public abstract class PunishableChallengeCommon extends ChallengeCommon implements Punishable {

    protected Collection<? extends Punishment> punishments;

    public PunishableChallengeCommon() {
        this(Set.of());
    }

    public PunishableChallengeCommon(Collection<? extends Punishment> punishments) {
        super();
        this.punishments = punishments;
    }

    @Override
    public Collection<? extends Punishment> getPunishments() {
        return punishments;
    }

    @Override
    public void setPunishments(Collection<? extends Punishment> punishments) {
        this.punishments = punishments;
    }
}
