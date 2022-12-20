package wand555.github.io.challengesreworked.logging;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import wand555.github.io.challengesreworked.Challenge;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.LanguageHandler;
import wand555.github.io.challengesreworked.punishment.Punishment;

import java.util.Map;

public class ChatLogger {

    private static final String PREFIX = "<GRADIENT:2C08BA>[ChallengesReworked]</GRADIENT:028A97>" ;

    public static void log(String path) {
        log(path, Map.of());
    }

    public static void log(String path, Map<String, Object> data) {
        Bukkit.broadcastMessage(format(path, data));
    }

    public static void logPlain(String msg) {
        Bukkit.broadcastMessage(msg);
    }

    public static void sendActionBar(BaseComponent... component) {
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component));
    }

    public static String format(String path, Map<String, Object> data) {
        return "%s %s".formatted(PREFIX, formatNoPrefix(path, data));
    }

    public static String formatNoPrefix(String path, Map<String, Object> data) {
        LanguageHandler languageHandler = ChallengeManager.getInstance().getLanguageHandler();
        String translated = languageHandler.get(path);
        String placeHoldersSet = PlaceHolderHandler.replacePlaceHolders(translated, data);
        //return IridiumColorAPI.process(placeHoldersSet);
        return placeHoldersSet;
    }
}
