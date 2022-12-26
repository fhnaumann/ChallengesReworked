package wand555.github.io.challengesreworked.challenges;

import wand555.github.io.challengesreworked.Commonable;
import wand555.github.io.challengesreworked.Storable;
import org.bukkit.inventory.ItemStack;

public interface Challenge extends Commonable, Storable {

    @Override
    ChallengeCommon getCommon();

    public ItemStack getUIDisplay();
}
