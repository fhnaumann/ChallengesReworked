package wand555.github.io.challengesreworked;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import wand555.github.io.challengesreworked.logging.ChatDisplay;

import java.util.Map;

public abstract class Challenge {

    protected final ChallengesReworked plugin;

    public Challenge() {
        this.plugin = JavaPlugin.getPlugin(ChallengesReworked.class);
    }

    public abstract ItemStack getUIDisplay();
}
