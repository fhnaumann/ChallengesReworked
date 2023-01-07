package wand555.github.io.challengesreworked.punishments;

import org.bukkit.entity.Player;

public abstract class PluginPunishment implements Punishment {

    private final PunishmentCommon common;

    public PluginPunishment(PunishmentCommon common) {
        this.common = common;
    }

    @Override
    public PunishmentCommon getCommon() {
        return common;
    }

    public void enforcePunishment(Player causer) {
        common.setAmountTriggered(common.getAmountTriggered()+1);
    }
}
