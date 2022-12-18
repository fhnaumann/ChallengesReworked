package wand555.github.io.challengesreworked;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.logging.ChatLogger;

public class ChallengeCommands implements org.bukkit.command.CommandExecutor {



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ChallengeManager manager = ChallengeManager.getInstance();
        if(command.getName().equalsIgnoreCase("start")) {
            try {
                //manager.loadDataFromFile();
                manager.start();

            } catch (IllegalStateException e) {
                ChatLogger.log("commands.start.alreadystarted");
            }
        }
        if(command.getName().equalsIgnoreCase("pause")) {
            manager.pause();
            manager.saveDataToFile();
        }
        if(command.getName().equalsIgnoreCase("resume")) {
            manager.loadDataFromFile();
            manager.resume();
        }
        if(command.getName().equalsIgnoreCase("end")) {

        }
        return false;
    }
}
