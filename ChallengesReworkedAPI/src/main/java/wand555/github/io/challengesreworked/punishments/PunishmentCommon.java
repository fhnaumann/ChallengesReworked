package wand555.github.io.challengesreworked.punishments;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.Common;

import java.util.Objects;

public abstract class PunishmentCommon implements Common, Comparable<PunishmentCommon> {

    private int amountTriggered;
    protected AffectType affectType;

    public PunishmentCommon() {
        this(0, AffectType.ALL);
    }

    public PunishmentCommon(int amountTriggered, AffectType affectType) {
        this.amountTriggered = amountTriggered;
        this.affectType = affectType;
    }

    public int getAmountTriggered() {
        return amountTriggered;
    }

    public void setAmountTriggered(int amountTriggered) {
        this.amountTriggered = amountTriggered;
    }

    public AffectType getAffectType() {
        return affectType;
    }

    public void setAffectType(AffectType affectType) {
        this.affectType = affectType;
    }

    @Override
    public PunishmentCommon copy() {
        throw new RuntimeException("Tried to copy abstract class");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PunishmentCommon that = (PunishmentCommon) o;
        return amountTriggered == that.amountTriggered && affectType == that.affectType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountTriggered, affectType);
    }

    @Override
    public int compareTo(@NotNull PunishmentCommon o) {
        //TODO: Implement actual comparing
        boolean sameClass = this.getClass().equals(o.getClass());
        if(!sameClass) {
            return this.getClass().getSimpleName().compareTo(o.getClass().getSimpleName());
        }
        return this.equals(o) ? 0 : 1;
    }
}
