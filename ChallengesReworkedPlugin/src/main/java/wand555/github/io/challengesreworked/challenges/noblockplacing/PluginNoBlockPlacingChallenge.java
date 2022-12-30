package wand555.github.io.challengesreworked.challenges.noblockplacing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.challenges.PluginPunishableChallenge;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class PluginNoBlockPlacingChallenge extends PluginPunishableChallenge implements NoBlockPlacingChallenge, Listener {

    public PluginNoBlockPlacingChallenge(NoBlockPlacingChallengeCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        Player player = event.getPlayer();
        if(!challengeManager.isInChallenge(player.getUniqueId())) {
            return;
        }
        Material placed = event.getBlockPlaced().getType();
        ChatLogger.log("challenges.noblockplacing.violation",
                Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, player,
                        PlaceHolderHandler.MATERIAL_PLACEHOLDER, placed));
        getPunishments().forEach(pluginPunishment -> pluginPunishment.enforcePunishment(player));

    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoBlockPlacingChallengeCommon getCommon() {
        return (NoBlockPlacingChallengeCommon) super.getCommon();
    }
}
