package wand555.github.io.challengesreworked.challenges.nodamage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.challenges.PluginPunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class PluginNoDamageChallenge extends PluginPunishableChallenge implements NoDamageChallenge, Listener {

    public PluginNoDamageChallenge(PunishableChallengeCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @EventHandler
    private void onDamageEvent(EntityDamageEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        if(!(event.getEntity() instanceof Player player)) {
            return;
        }
        if(!challengeManager.isInChallenge(player.getUniqueId())) {
            return;
        }
        if(event.getFinalDamage() <= 0) {
            return;
        }
        Object damager = null;
        if(event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
            damager = entityDamageByEntityEvent.getDamager().getType();
        }
        else if(event instanceof EntityDamageByBlockEvent entityDamageByBlockEvent) {
            damager = entityDamageByBlockEvent.getDamager().getType();
        }
        ChatLogger.log("challenges.nodamage.violation", Map.of(
                PlaceHolderHandler.PLAYER_PLACEHOLDER, player,
                PlaceHolderHandler.AMOUNT_PLACEHOLDER, event.getFinalDamage(),
                PlaceHolderHandler.ENTITY_PLACEHOLDER, damager
        ));
        getPunishments().forEach(pluginPunishment -> pluginPunishment.enforcePunishment(player));
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoDamageChallengeCommon getCommon() {
        return (NoDamageChallengeCommon) super.getCommon();
    }
}
