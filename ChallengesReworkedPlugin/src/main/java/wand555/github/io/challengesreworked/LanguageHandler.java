package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LanguageHandler {




    private final Map<Locale, YamlDocument> languageMessages;
    private Locale currentLocale;

    public LanguageHandler() {
        this.languageMessages = new HashMap<>();
        languageMessages.put(Locale.ENGLISH, open(Locale.ENGLISH));
        //languageMessages.put(Locale.GERMAN, open(Locale.GERMAN));
        System.out.println(Locale.ENGLISH.getLanguage());
    }

    public String get(String path) {
        return languageMessages.get(currentLocale).getString(path);
    }



    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    private static YamlDocument open(Locale locale) {
        ChallengesReworked plugin = ChallengesReworked.getPlugin(ChallengesReworked.class);
        try {
            return YamlDocument.create(
                    new File(plugin.getDataFolder(), locale.getDisplayLanguage()+"_messages.yml"),
                    plugin.getResource(locale.getLanguage()+"_messages.yml")
            );
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to load messages.yml file!", e);
        }
        return null;
    }
}
