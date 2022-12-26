package wand555.github.io.challengesreworked.challenges.nocrafting;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.challenges.PluginPunishableChallenge;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class PluginNoCraftingChallenge extends PluginPunishableChallenge implements NoCraftingChallenge, Listener {
    public PluginNoCraftingChallenge(NoCraftingChallengeCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @EventHandler
    private void onCraft(InventoryClickEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        if(event.getRawSlot() != 0) {
            return;
        }
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if(!challengeManager.isInChallenge(player.getUniqueId())) {
            return;
        }

        InventoryType inventoryType = event.getInventory().getType();
        if(!getCommon().getForbiddenToUse().contains(inventoryType)) {
            return;
        }
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) {
            return;
        }
        if(getCommon().getAllowedToCraft().contains(event.getCurrentItem().getType())) {
            return;
        }
        ChatLogger.log("challenges.nocrafting.violation",
                Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, player,
                        PlaceHolderHandler.MATERIAL_PLACEHOLDER, event.getCurrentItem().getType()));
        getPunishments().forEach(pluginPunishment -> pluginPunishment.enforcePunishment(player));
    }



    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoCraftingChallengeCommon getCommon() {
        return (NoCraftingChallengeCommon) super.getCommon();
    }

    @Override
    public void register() {

    }
}
