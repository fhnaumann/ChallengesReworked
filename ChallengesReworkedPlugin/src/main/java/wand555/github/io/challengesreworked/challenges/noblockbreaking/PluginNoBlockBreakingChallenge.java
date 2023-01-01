package wand555.github.io.challengesreworked.challenges.noblockbreaking;

import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.challenges.PluginPunishableChallenge;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class PluginNoBlockBreakingChallenge extends PluginPunishableChallenge implements NoBlockBreakingChallenge, Listener {

    public PluginNoBlockBreakingChallenge(NoBlockBreakingChallengeCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @EventHandler
    private void onBlockBreakEvent(BlockBreakEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        Player player = event.getPlayer();
        if(!challengeManager.isInChallenge(player.getUniqueId())) {
            return;
        }
        Material broken = event.getBlock().getType();
        if(getCommon().getAllowedToBreak().contains(broken)) {
            return;
        }
        ChatLogger.log("challenges.noblockbreaking.violation",
                Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, player,
                        PlaceHolderHandler.MATERIAL_PLACEHOLDER, broken));
        getPunishments().forEach(pluginPunishment -> pluginPunishment.enforcePunishment(player));

    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoBlockBreakingChallengeCommon getCommon() {
        return (NoBlockBreakingChallengeCommon) super.getCommon();
    }
}
