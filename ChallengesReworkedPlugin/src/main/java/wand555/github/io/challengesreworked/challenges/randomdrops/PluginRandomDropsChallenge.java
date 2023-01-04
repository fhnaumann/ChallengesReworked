package wand555.github.io.challengesreworked.challenges.randomdrops;

import org.bukkit.inventory.ItemStack;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.PluginChallenge;

public class PluginRandomDropsChallenge extends PluginChallenge implements RandomDropsChallenge {
    public PluginRandomDropsChallenge(RandomDropsChallengeCommon common) {
        super(common);
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public RandomDropsChallengeCommon getCommon() {
        return (RandomDropsChallengeCommon) super.getCommon();
    }
}
