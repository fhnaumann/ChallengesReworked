package wand555.github.io.challengesreworked.punishment;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.logging.ChatDisplay;

import java.util.HashMap;
import java.util.Map;

public abstract class Punishment {

    private int amountTriggered;
    protected final AffectType affectType;

    public Punishment(int amountTriggered, AffectType affectType) {
        this.amountTriggered = amountTriggered;
        this.affectType = affectType;
    }

    public void enforcePunishment(Player causer) {
        amountTriggered++;
    }

    protected int getAmountTriggered() {
        return amountTriggered;
    }

    protected AffectType getAffectType() {
        return affectType;
    }
}
