package wand555.github.io.challengesreworked.challenges;

import wand555.github.io.challengesreworked.Commonable;
import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.goals.Goal;
import wand555.github.io.challengesreworked.punishments.Punishment;

import java.util.Collection;
import java.util.List;

public interface Challenge extends Commonable {

    @Override
    ChallengeCommon getCommon();

    public ItemStack getUIDisplay();
}
