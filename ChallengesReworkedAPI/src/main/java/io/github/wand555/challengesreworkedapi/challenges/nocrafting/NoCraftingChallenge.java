package io.github.wand555.challengesreworkedapi.challenges.nocrafting;

import io.github.wand555.challengesreworkedapi.challenges.PunishableChallenge;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;

import java.util.Collection;
import java.util.Set;

public interface NoCraftingChallenge extends PunishableChallenge {

    @Override
    NoCraftingChallengeCommon getCommon();

}
