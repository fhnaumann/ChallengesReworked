package wand555.github.io.challengesreworked.punishments.inventoryclear;

import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.endchallenge.EndChallengePunishment;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishment;

import java.util.Collection;
import java.util.List;

public interface InventoryClearPunishment extends Punishment {

    @Override
    InventoryClearPunishmentCommon getCommon();

    @Override
    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of(
                EndChallengePunishment.class,
                RandomItemPunishment.class
        );
    }
}
