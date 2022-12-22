package wand555.github.io.challengesreworked.punishment;

import io.github.wand555.challengesreworkedapi.punishments.AffectType;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;
import org.bukkit.entity.Player;

public abstract class PluginPunishment implements Punishment {

    private final PunishmentCommon common;

    public PluginPunishment(PunishmentCommon common) {
        this.common = common;
        register();
    }

    @Override
    public PunishmentCommon getCommon() {
        return common;
    }

    public void enforcePunishment(Player causer) {
        common.setAmountTriggered(common.getAmountTriggered()+1);
    }
}
