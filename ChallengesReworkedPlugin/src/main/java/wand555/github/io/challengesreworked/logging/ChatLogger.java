package wand555.github.io.challengesreworked.logging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.LanguageHandler;

import java.util.Map;

public class ChatLogger {

    private static final Component PREFIX = MiniMessage.miniMessage().deserialize("<gradient:#2C08BA:#028A97>[ChallengesReworked]</gradient>");

    public static void log(String path) {
        log(path, Map.of());
    }

    public static void log(String path, Map<String, Object> data) {
        Bukkit.getServer().sendMessage(format(path, data));
    }

    public static void logPlain(String msg) {
        Bukkit.broadcastMessage(msg);
    }

    public static void sendActionBar(String path, Map<String, Object> data) {
        Bukkit.getServer().sendActionBar(formatNoPrefix(path, data));
    }

    public static Component format(String path, Map<String, Object> data) {
        return PREFIX.appendSpace().append(formatNoPrefix(path, data));
    }

    public static Component formatNoPrefix(String path, Map<String, Object> data) {
        LanguageHandler languageHandler = ChallengeManager.getInstance().getLanguageHandler();
        String translated = languageHandler.get(path);
        String placeHoldersSet = PlaceHolderHandler.replacePlaceHolders(translated, data);
        return MiniMessage.miniMessage().deserialize(placeHoldersSet);
    }
}
