package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import wand555.github.io.challengesreworked.goal.ChallengeEnding;
import wand555.github.io.challengesreworked.goal.Goal;
import wand555.github.io.challengesreworked.goal.ItemCollectGoal;
import wand555.github.io.challengesreworked.goal.MobGoal;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.punishment.AffectType;
import wand555.github.io.challengesreworked.punishment.HealthPunishment;
import wand555.github.io.challengesreworked.punishment.Punishment;
import wand555.github.io.challengesreworked.punishment.RandomItemPunishment;
import wand555.github.io.challengesreworked.timer.TimeOrder;
import wand555.github.io.challengesreworked.timer.Timer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChallengeManager {

    private static ChallengeManager instance;

    private final ChallengesReworked plugin;
    private final NamespacedKey key;

    private Collection<UUID> players;
    private GameState gameState;
    private Collection<Challenge> activeChallenges;
    private Collection<Goal> goals;
    private Timer timer;


    private final LanguageHandler languageHandler;

    private ChallengeManager() {

        this.plugin = JavaPlugin.getPlugin(ChallengesReworked.class);
        this.key = new NamespacedKey(plugin, "already_collected");
        this.gameState = GameState.SET_UP;
        this.players = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toSet());
        //this.players = new HashSet<>();
        this.languageHandler = new LanguageHandler();
        languageHandler.setCurrentLocale(Locale.ENGLISH);
        this.activeChallenges = new HashSet<>();
        this.goals = new HashSet<>();
        this.timer = new Timer(TimeOrder.ASC);
    }

    public void start() throws IllegalStateException{
        /*if(gameState != GameState.SET_UP) {
            throw new IllegalStateException("TODODODODO");
        }*/
        Set<Punishment> punishments = Set.of(
                new RandomItemPunishment(0, AffectType.CAUSER, 1),
                new HealthPunishment(0, AffectType.CAUSER, 2));
        NoCraftingChallenge challenge = new NoCraftingChallenge(
                punishments,
                Set.of(Material.ENDER_EYE),
                Set.of(InventoryType.WORKBENCH));
        activeChallenges.add(challenge);
        Goal mobGoal = new MobGoal(EntityType.PIG, 2);
        goals.add(mobGoal);
        Map<Material, Collect> map = new HashMap<>();
        map.put(Material.STONE, new Collect(3));
        map.put(Material.COBBLESTONE, new Collect(2));
        goals.add(new ItemCollectGoal(map));
        timer.start();
        gameState = GameState.RUNNING;
        ChatLogger.log("run.start");
    }

    public void pause() throws IllegalStateException {
        /*if(gameState != GameState.RUNNING) {
            throw new IllegalStateException();
        }*/
        gameState = GameState.PAUSED;
    }

    public void resume() throws IllegalStateException {
        if(gameState != GameState.PAUSED) {
            throw new IllegalStateException();
        }
        gameState = GameState.RUNNING;
    }

    public void end(ChallengeEnding ending) {
        players.forEach(uuid -> Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR));

        gameState = GameState.ENDED;
        ChatLogger.log("run.end");
    }

    public void saveDataToFile() {
        try {
            YamlDocument storage = YamlDocument.create(new File(plugin.getDataFolder(), "data_storage.yml"));
            //storage.setGeneralSettings(GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build());
            storage.clear();
            //storage.set("players", players);
            storage.set("time", timer.getElapsedTime());
            gameState = GameState.PAUSED;
            storage.set("state", gameState.toString());
            storage.set("goals", new ArrayList<>(goals));
            storage.set("challenges", new ArrayList<>(activeChallenges));
            storage.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromFile() {
        try {
            YamlDocument storage = YamlDocument.create(
                    new File(plugin.getDataFolder(), "data_storage.yml"));
            //storage.setGeneralSettings(GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build());
            //players = (Collection<UUID>) storage.getList("players");
            long elapsedTime = storage.getLong("time", 0L);
            timer = new Timer(elapsedTime, TimeOrder.ASC);
            timer.start();
            gameState = storage.getEnum("state", GameState.class, GameState.SET_UP);
            goals = (Collection<Goal>) storage.getList("goals", new ArrayList<>());
            activeChallenges = (Collection<Challenge>) storage.getList("challenges", new ArrayList<>());
            System.out.println(activeChallenges);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean allGoalsComplete() {
        return goals.stream().allMatch(Goal::isComplete);
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    public boolean isInChallenge(UUID uuid) {
        return players.contains(uuid);
    }

    public Collection<UUID> getPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }

    public ChallengesReworked getPlugin() {
        return plugin;
    }

    public PluginManager getPluginManager() {
        return plugin.getServer().getPluginManager();
    }

    public NamespacedKey getKey() {
        return key;
    }

    public static ChallengeManager getInstance() {
        if(instance == null) {
            instance = new ChallengeManager();
        }
        return instance;
    }
}
