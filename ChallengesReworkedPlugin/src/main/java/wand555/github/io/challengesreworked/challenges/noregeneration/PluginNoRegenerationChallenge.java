package wand555.github.io.challengesreworked.challenges.noregeneration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.PluginChallenge;

import java.util.*;

public class PluginNoRegenerationChallenge extends PluginChallenge implements NoRegenerationChallenge, Listener {

    public PluginNoRegenerationChallenge(NoRegenerationChallengeCommon common) {
        super(common);
        ChallengesReworked.registerListener(plugin, this);
    }

    @Override
    public ItemStack getUIDisplay() {
        return null;
    }

    @Override
    public NoRegenerationChallengeCommon getCommon() {
        return (NoRegenerationChallengeCommon) super.getCommon();
    }
}
