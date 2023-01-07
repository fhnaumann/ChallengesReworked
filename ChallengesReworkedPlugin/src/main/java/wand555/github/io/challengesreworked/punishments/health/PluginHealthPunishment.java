package wand555.github.io.challengesreworked.punishments.health;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;
import wand555.github.io.challengesreworked.punishments.PluginPunishment;

import java.util.Map;
import java.util.Objects;

public class PluginHealthPunishment extends PluginPunishment implements HealthPunishment {

    public PluginHealthPunishment(HealthPunishmentCommon common) {
        super(common);
    }


    @Override
    public void enforcePunishment(Player causer) {
        super.enforcePunishment(causer);
        switch(getCommon().getAffectType()) {
            case CAUSER -> {
                damagePlayer(causer);
                ChatLogger.log("punishments.health.affecttype.causer",
                        Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, causer,
                                PlaceHolderHandler.AMOUNT_PLACEHOLDER, getCommon().getHealthAmount()
                        ));
            }
            case ALL -> {
                ChallengeManager.getInstance().getPlayers().stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(this::damagePlayer);
                ChatLogger.log("punishments.health.affecttype.all",
                        Map.of(PlaceHolderHandler.AMOUNT_PLACEHOLDER, getCommon().getHealthAmount()));
            }
        }
    }

    private void damagePlayer(Player player) {
        player.damage(getCommon().getHealthAmount());
    }

    @Override
    public HealthPunishmentCommon getCommon() {
        return (HealthPunishmentCommon) super.getCommon();
    }
}
