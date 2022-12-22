package io.github.wand555.challengesreworkedapi.punishments;

import io.github.wand555.challengesreworkedapi.Common;
import org.bukkit.entity.Player;

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
