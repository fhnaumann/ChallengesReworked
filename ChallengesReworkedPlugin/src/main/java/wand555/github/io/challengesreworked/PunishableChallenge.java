package wand555.github.io.challengesreworked;

import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.punishment.Punishable;
import wand555.github.io.challengesreworked.punishment.Punishment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public abstract class PunishableChallenge extends Challenge implements Punishable {

    protected Collection<Punishment> punishments;

    public PunishableChallenge(Collection<Punishment> punishments) {
        super();
        this.punishments = punishments;
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public Collection<Punishment> getPunishments() {
        return null;
    }

    @Override
    public void setPunishments(Collection<Punishment> punishments) {
        this.punishments = punishments;
    }
}
