package wand555.github.io.challengesreworked.goals.itemcollect;

import wand555.github.io.challengesreworked.Collect;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.goals.ChallengeEnding;
import wand555.github.io.challengesreworked.goals.PluginGoal;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;

public class PluginItemCollectGoal extends PluginGoal implements ItemCollectGoal, Listener {

    private static final String MARKED = "true";

    public PluginItemCollectGoal(ItemCollectGoalCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @Override
    public void onGoalReached() {
        if(isComplete()) {
            return;
        }
        ChallengeManager manager = ChallengeManager.getInstance();
        setComplete(true);
        ChatLogger.log("goals.itemcollectgoal.complete", Map.of(
                PlaceHolderHandler.AMOUNT_PLACEHOLDER, getCommon().getToCollect().keySet().size()
        ));
        if(manager.allGoalsComplete()) {
            manager.end(ChallengeEnding.SUCCESS);
        }
    }

    @Override
    public boolean isGoalReached() {
        return getCommon().getToCollect().values().stream().allMatch(Collect::isReached);
    }

    @EventHandler
    private void pickupEvent(EntityPickupItemEvent event) {
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
        newMaterialDetected(player, event.getItem().getItemStack());
    }

    @EventHandler
    private void invEvent(InventoryClickEvent event) {
        ChallengeManager challengeManager = ChallengeManager.getInstance();
        if(!challengeManager.isRunning()) {
            return;
        }
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if(!challengeManager.isInChallenge(player.getUniqueId())) {
            return;
        }
        if(event.getCurrentItem() != null) {
            System.out.println("current" + event.getCurrentItem());
            System.out.println("cursor " + event.getCursor());
            //don't do anything if item is directly dropped (not considered to be collected)
            if(event.getClick() == ClickType.CONTROL_DROP || event.getClick() == ClickType.DROP) {
                return;
            }
            if(event.getAction() == InventoryAction.PICKUP_HALF) {
                //newMaterialDetected(player, );
                return; //will be handled later when the player places it in the inventory
            }
            newMaterialDetected(player, event.getCurrentItem());
        }
        if(event.getCursor() != null) {
            newMaterialDetected(player, event.getCursor());
        }
        Inventory inv = event.getWhoClicked().getInventory();
        for(ItemStack itemStack : inv.getContents()) {
            if(itemStack != null && itemStack.getType().isItem() && !itemStack.getType().isAir()) {
                newMaterialDetected(player, itemStack);
            }
        }
    }

    private void newMaterialDetected(Player collectedBy, ItemStack newDetected) {
        getCommon().getToCollect().computeIfPresent(newDetected.getType(), (material, collect) -> {
            if(!isMarked(newDetected)) {
                markItemStack(newDetected);
                //collectedBy.updateInventory();
                collect.setCurrentAmount(collect.getCurrentAmount()+newDetected.getAmount());
                ChatLogger.log("goals.itemcollectgoal.success", Map.of(
                        PlaceHolderHandler.PLAYER_PLACEHOLDER, collectedBy,
                        PlaceHolderHandler.MATERIAL_PLACEHOLDER, material,
                        PlaceHolderHandler.CURRENT_AMOUNT_PLACEHOLDER, collect.getCurrentAmount(),
                        PlaceHolderHandler.AMOUNT_NEEDED_PLACEHOLDER, collect.getAmountNeeded()
                ));
            }
            return collect;
        });
        if(isGoalReached()) {
            onGoalReached();
        }
    }

    private boolean isMarked(ItemStack itemStack) {
        NamespacedKey key = ChallengeManager.getInstance().getKey();
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(key, PersistentDataType.STRING) && pdc.get(key, PersistentDataType.STRING).equals(MARKED);
    }

    private void markItemStack(ItemStack itemStack) {
        NamespacedKey key = ChallengeManager.getInstance().getKey();
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        System.out.println("marked");
        pdc.set(key, PersistentDataType.STRING, MARKED);
        itemStack.setItemMeta(meta);
        System.out.println(itemStack.getItemMeta());
    }

    @Override
    public ItemCollectGoalCommon getCommon() {
        return (ItemCollectGoalCommon) super.getCommon();
    }

    @Override
    public void register() {
    }
}
