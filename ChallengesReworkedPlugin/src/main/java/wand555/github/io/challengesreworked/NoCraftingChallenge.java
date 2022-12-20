package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;
import wand555.github.io.challengesreworked.punishment.Punishment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NoCraftingChallenge extends PunishableChallenge implements Listener {

    private final Set<Material> allowedToCraft;
    private final Set<InventoryType> forbiddenToUse;

    public NoCraftingChallenge(Collection<Punishment> punishments, Set<Material> allowedToCraft, Set<InventoryType> forbiddenToUse) {
        super(punishments);
        this.allowedToCraft = allowedToCraft;
        this.forbiddenToUse = forbiddenToUse;
        if(HandlerList.getRegisteredListeners(plugin).stream().noneMatch(registeredListener -> registeredListener.getListener().getClass() == this.getClass())) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
        StandardSerializer.getDefault().register(NoCraftingChallenge.class, adapter);
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
        System.out.println("1");
        if(!forbiddenToUse.contains(inventoryType)) {
            return;
        }
        System.out.println("2");
        if(event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) {
            return;
        }
        if(allowedToCraft.contains(event.getCurrentItem().getType())) {
            return;
        }
        ChatLogger.log("challenges.nocraftingchallenge.violation",
                Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, player,
                        PlaceHolderHandler.MATERIAL_PLACEHOLDER, event.getCurrentItem().getType()));
        punishments.forEach(punishment -> punishment.enforcePunishment(player));
    }



    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    private final TypeAdapter<NoCraftingChallenge> adapter = new TypeAdapter<NoCraftingChallenge>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoCraftingChallenge noCraftingChallenge) {
            Map<Object, Object> map = new HashMap<>();
            map.put("punishments", new ArrayList<>(punishments));
            map.put("allowedToCraft", allowedToCraft.stream().map(Enum::toString).collect(Collectors.toList()));
            map.put("forbiddenToUse", forbiddenToUse.stream().map(Enum::toString).collect(Collectors.toList()));
            return map;
        }

        @NotNull
        @Override
        public NoCraftingChallenge deserialize(@NotNull Map<Object, Object> map) {
            System.out.println("des");
            return new NoCraftingChallenge(
                    (List<Punishment>) map.get("punishments"),
                    ((List<?>) map.get("allowedToCraft")).stream().map(o -> Material.valueOf(o.toString())).collect(Collectors.toSet()),
                    ((List<?>) map.get("forbiddenToUse")).stream().map(o -> InventoryType.valueOf(o.toString())).collect(Collectors.toSet()));
        }
    };
}
