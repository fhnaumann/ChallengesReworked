package wand555.github.io.challengesreworked.punishments;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Common;

public abstract class PunishmentCommon implements Common {

    private int amountTriggered;
    protected AffectType affectType;

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

}
