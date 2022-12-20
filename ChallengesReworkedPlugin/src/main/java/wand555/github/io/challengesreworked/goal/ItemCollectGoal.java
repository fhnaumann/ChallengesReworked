package wand555.github.io.challengesreworked.goal;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemCollectGoal extends Goal implements Listener {

    private static final String MARKED = "true";

    private final Map<Material, Collect> toCollect;

    public ItemCollectGoal(boolean complete, Map<Material, Collect> toCollect) {
        super(complete);
        this.toCollect = toCollect;
        JavaPlugin plugin = ChallengeManager.getInstance().getPlugin();
        if(HandlerList.getRegisteredListeners(plugin).stream().noneMatch(registeredListener -> registeredListener.getListener().getClass() == this.getClass())) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
        StandardSerializer.getDefault().register(ItemCollectGoal.class, adapter);
    }

    public ItemCollectGoal(Map<Material, Collect> toCollect) {
        this(false, toCollect);
    }

    @Override
    public void onGoalReached() {
        if(isComplete()) {
            return;
        }
        ChallengeManager manager = ChallengeManager.getInstance();
        setComplete(true);
        ChatLogger.log("goals.itemcollectgoal.complete", Map.of(
                PlaceHolderHandler.AMOUNT_PLACEHOLDER, toCollect.keySet().size()
        ));
        if(manager.allGoalsComplete()) {
            manager.end(ChallengeEnding.SUCCESS);
        }
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
        toCollect.computeIfPresent(newDetected.getType(), (material, collect) -> {
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
        checkGoalReached();
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

    private void checkGoalReached() {
        if(toCollect.values().stream().allMatch(Collect::reached)) {
            onGoalReached();
        }
    }

    private final TypeAdapter<ItemCollectGoal> adapter = new TypeAdapter<ItemCollectGoal>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull ItemCollectGoal itemCollectGoal) {
            return Map.of(
                    "complete", isComplete(),
                    "toCollect", toCollect.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue))
            );
        }

        @NotNull
        @Override
        public ItemCollectGoal deserialize(@NotNull Map<Object, Object> map) {
            return new ItemCollectGoal((boolean) map.get("complete"), ((Map<?, ?>) map.get("toCollect")).entrySet().stream()
                    .collect(Collectors.toMap(entry -> Material.valueOf(entry.getKey().toString()), entry -> (Collect) entry.getValue())));
        }
    };

}
