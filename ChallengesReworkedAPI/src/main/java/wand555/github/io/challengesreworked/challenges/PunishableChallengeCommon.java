package wand555.github.io.challengesreworked.challenges;

import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class PunishableChallengeCommon extends ChallengeCommon {

    protected Collection<PunishmentCommon> punishmentCommons;

    public PunishableChallengeCommon() {
        this(new TreeSet<>());
    }

    public PunishableChallengeCommon(Collection<PunishmentCommon> punishmentCommons) {
        super();
        System.out.println(punishmentCommons.getClass());
        this.punishmentCommons = punishmentCommons;
    }

    public Collection<PunishmentCommon> getPunishmentCommons() {
        return punishmentCommons;
    }

    public Collection<PunishmentCommon> copyPunishmentCommons() {
        return getPunishmentCommons().stream().map(PunishmentCommon::copy).collect(Collectors.toCollection(TreeSet::new));
    }

    public void setPunishmentCommons(Collection<PunishmentCommon> punishmentCommons) {
        this.punishmentCommons = punishmentCommons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PunishableChallengeCommon that = (PunishableChallengeCommon) o;
        return Objects.equals(punishmentCommons, that.punishmentCommons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(punishmentCommons);
    }
}
