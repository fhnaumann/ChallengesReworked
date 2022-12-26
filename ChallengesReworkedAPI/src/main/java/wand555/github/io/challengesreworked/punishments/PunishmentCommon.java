package wand555.github.io.challengesreworked.punishments;

import wand555.github.io.challengesreworked.Common;

public abstract class PunishmentCommon implements Common {

    private int amountTriggered;
    protected final AffectType affectType;

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
}
