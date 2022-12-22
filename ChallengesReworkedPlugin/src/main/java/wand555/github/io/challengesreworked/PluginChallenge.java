package wand555.github.io.challengesreworked;

import io.github.wand555.challengesreworkedapi.challenges.Challenge;
import io.github.wand555.challengesreworkedapi.challenges.ChallengeCommon;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginChallenge implements Challenge {

    private final ChallengeCommon common;

    protected final ChallengesReworked plugin;

    public PluginChallenge(ChallengeCommon common) {
        this.common = common;
        this.plugin = JavaPlugin.getPlugin(ChallengesReworked.class);
        register();
    }

    @Override
    public ChallengeCommon getCommon() {
        return common;
    }

    public abstract ItemStack getUIDisplay();
}
