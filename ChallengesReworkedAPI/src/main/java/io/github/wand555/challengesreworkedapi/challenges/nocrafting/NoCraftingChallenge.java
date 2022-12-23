package io.github.wand555.challengesreworkedapi.challenges.nocrafting;

import io.github.wand555.challengesreworkedapi.challenges.PunishableChallenge;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;

import java.util.Collection;
import java.util.Set;

public interface NoCraftingChallenge extends PunishableChallenge {

    @Override
    NoCraftingChallengeCommon getCommon();

    @Override
    default Collection<? extends Punishment> getPunishments() {
        return getCommon().getPunishments();
    }

    @Override
    default void setPunishments(Collection<? extends Punishment> punishments) {
        getCommon().setPunishments(punishments);
    }
}
