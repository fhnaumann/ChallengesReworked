package wand555.github.io.challengesreworked.punishment.randomitem;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import io.github.wand555.challengesreworkedapi.punishments.randomitem.RandomItemPunishment;
import io.github.wand555.challengesreworkedapi.punishments.randomitem.RandomItemPunishmentCommon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;
import wand555.github.io.challengesreworked.punishment.PluginPunishment;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class PluginRandomItemPunishment extends PluginPunishment implements RandomItemPunishment {

    public PluginRandomItemPunishment(RandomItemPunishmentCommon common) {
        super(common);
    }

    @Override
    public void enforcePunishment(Player causer) {
        super.enforcePunishment(causer);
        switch(getCommon().getAffectType()) {
            case CAUSER -> {
                removeRandomItem(causer).ifPresentOrElse(itemStack -> {
                    ChatLogger.log("punishments.randomitem.affecttype.causer.success", Map.of(
                            PlaceHolderHandler.ITEMSTACK_PLACEHOLDER, itemStack,
                            PlaceHolderHandler.PLAYER_PLACEHOLDER, causer
                            ));
                }, () -> {
                    ChatLogger.log("punishments.randomitem.affecttype.causer.failed", Map.of(
                            PlaceHolderHandler.PLAYER_PLACEHOLDER, causer
                    ));
                });
            }
            case ALL -> {
                ChatLogger.log("punishments.randomitem.affecttype.all");
                ChallengeManager.getInstance().getPlayers().stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(player -> {
                            removeRandomItem(player).ifPresentOrElse(itemStack -> {
                                ChatLogger.log("punishments.randomitem.affecttype.all.perplayer.success", Map.of(
                                        PlaceHolderHandler.ITEMSTACK_PLACEHOLDER, itemStack
                                ));
                            }, () -> {
                                ChatLogger.log("punishments.randomitem.affecttype.all.perplayer.success");
                            });
                        });
            }
        }
    }

    private Optional<ItemStack> removeRandomItem(Player player) {
        List<ItemStack> playerItems = Stream.of(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .toList();
        if(playerItems.size() > 0) {
            ItemStack toRemove = playerItems.get(ThreadLocalRandom.current().nextInt(0, playerItems.size()));
            player.getInventory().remove(toRemove);
            return Optional.of(toRemove);
        }
        return Optional.empty();
    }

    @Override
    public RandomItemPunishmentCommon getCommon() {
        return (RandomItemPunishmentCommon) super.getCommon();
    }

    @Override
    public void register() {
    }
}
