package wand555.github.io.challengesreworked.punishment;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class RandomItemPunishment extends Punishment {

    private final int howManyRemoved;

    public RandomItemPunishment(int amountTriggered, AffectType affectType, int howManyRemoved) {
        super(amountTriggered, affectType);
        this.howManyRemoved = howManyRemoved;
        StandardSerializer.getDefault().register(RandomItemPunishment.class, typeAdapter);
    }

    @Override
    public void enforcePunishment(Player causer) {
        super.enforcePunishment(causer);
        switch(affectType) {
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


    private final TypeAdapter<RandomItemPunishment> typeAdapter = new TypeAdapter<RandomItemPunishment>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull RandomItemPunishment randomItemPunishment) {
            return Map.of(
                    "amountTriggered", getAmountTriggered(),
                    "affectType", getAffectType().toString(),
                    "howManyRemoved", howManyRemoved
            );
        }

        @NotNull
        @Override
        public RandomItemPunishment deserialize(@NotNull Map<Object, Object> map) {
            return new RandomItemPunishment(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString()),
                    (int) map.get("howManyRemoved")
            );
        }
    };
}
