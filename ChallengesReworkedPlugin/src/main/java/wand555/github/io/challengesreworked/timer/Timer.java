package wand555.github.io.challengesreworked.timer;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.logging.ChatLogger;

import java.util.Map;

public class Timer extends BukkitRunnable {
    private final TimeOrder timeOrder;
    private long elapsedTime;

    public Timer(TimeOrder timeOrder) {
        this(0, timeOrder);
    }

    public Timer(long elapsedTime, TimeOrder timeOrder) {
        this.elapsedTime = elapsedTime;
        this.timeOrder = timeOrder;
    }

    public void start() {
        runTaskTimer(JavaPlugin.getPlugin(ChallengesReworked.class), 0L, 1L);
    }

    @Override
    public void run() {
        ChallengeManager manager = ChallengeManager.getInstance();
        String displayTime = DateUtil.formatDuration(elapsedTime /20);
        ChatLogger.sendActionBar("timer.running", Map.of("time", displayTime));
        if(manager.isRunning()) {
            elapsedTime++;
        }

    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
