package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

public final class ChallengesReworked extends JavaPlugin {

    public ChallengesReworked() {
        super();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        CommandExecutor ce = new ChallengeCommands();
        getCommand("start").setExecutor(ce);
        getCommand("pause").setExecutor(ce);
        getCommand("resume").setExecutor(ce);
        getCommand("end").setExecutor(ce);

        Bukkit.getScheduler().runTaskLater(this, () -> {
                ChallengeManager manager = ChallengeManager.getInstance();
                //manager.loadDataFromFile();
                // manager.start();
        }, 0L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //ChallengeManager manager = ChallengeManager.getInstance();

        //manager.saveDataToFile();

    }

    public static <T extends Listener> T registerListener(JavaPlugin plugin, T listener) {
        if(HandlerList.getRegisteredListeners(plugin).stream().noneMatch(registeredListener -> registeredListener.getListener().getClass() == listener.getClass())) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
        return listener;
    }
}
