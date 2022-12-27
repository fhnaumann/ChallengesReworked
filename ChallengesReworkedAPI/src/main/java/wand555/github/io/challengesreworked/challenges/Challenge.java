package wand555.github.io.challengesreworked.challenges;

import wand555.github.io.challengesreworked.Commonable;
import org.bukkit.inventory.ItemStack;

public interface Challenge extends Commonable {

    @Override
    ChallengeCommon getCommon();

    public ItemStack getUIDisplay();
}
