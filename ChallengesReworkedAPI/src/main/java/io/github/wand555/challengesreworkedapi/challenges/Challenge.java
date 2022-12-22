package io.github.wand555.challengesreworkedapi.challenges;

import io.github.wand555.challengesreworkedapi.Commonable;
import io.github.wand555.challengesreworkedapi.Storable;
import io.github.wand555.challengesreworkedapi.challenges.ChallengeCommon;
import org.bukkit.inventory.ItemStack;

public interface Challenge extends Commonable, Storable {

    @Override
    ChallengeCommon getCommon();

    public abstract ItemStack getUIDisplay();
}
