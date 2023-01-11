package wand555.github.io.challengesreworked.punishments.endchallenge;

import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.death.DeathPunishment;
import wand555.github.io.challengesreworked.punishments.health.HealthPunishment;
import wand555.github.io.challengesreworked.punishments.inventoryclear.InventoryClearPunishment;
import wand555.github.io.challengesreworked.punishments.inventoryclear.InventoryClearPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomeffect.RandomEffectPunishment;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishment;

import java.util.Collection;
import java.util.List;

public interface EndChallengePunishment extends Punishment {

    @Override
    EndChallengePunishmentCommon getCommon();

    @Override
    default Collection<Class<? extends Punishment>> getIncompatiblePunishments() {
        return List.of(
                DeathPunishment.class,
                HealthPunishment.class,
                InventoryClearPunishment.class,
                RandomEffectPunishment.class,
                RandomItemPunishment.class
        );
    }
}
