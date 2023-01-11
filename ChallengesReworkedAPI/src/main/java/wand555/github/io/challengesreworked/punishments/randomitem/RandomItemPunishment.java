package wand555.github.io.challengesreworked.punishments.randomitem;

import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.endchallenge.EndChallengePunishment;
import wand555.github.io.challengesreworked.punishments.inventoryclear.InventoryClearPunishment;

import java.util.Collection;
import java.util.List;

public interface RandomItemPunishment extends Punishment {
    @Override
    RandomItemPunishmentCommon getCommon();

    @Override
    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of(
                EndChallengePunishment.class,
                InventoryClearPunishment.class
        );
    }

    /*
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


     */
}
