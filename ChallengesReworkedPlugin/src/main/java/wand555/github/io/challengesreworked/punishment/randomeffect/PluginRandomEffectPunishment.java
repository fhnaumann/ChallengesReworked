package wand555.github.io.challengesreworked.punishment.randomeffect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.ChallengesReworked;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;
import wand555.github.io.challengesreworked.punishment.PluginPunishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomeffect.RandomEffectPunishment;
import wand555.github.io.challengesreworked.punishments.randomeffect.RandomEffectPunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PluginRandomEffectPunishment extends PluginPunishment implements RandomEffectPunishment {

    public PluginRandomEffectPunishment(RandomEffectPunishmentCommon common) {
        super(common);
    }

    @Override
    public void enforcePunishment(Player causer) {
        super.enforcePunishment(causer);
        switch(getCommon().getAffectType()) {
            case CAUSER -> {
                Collection<PotionEffect> appliedPotionEffects = applyPotionEffects(List.of(causer), getCommon().getHowManyEffects());
                appliedPotionEffects.forEach(potionEffect -> {
                    ChatLogger.log("randompotioneffect.affecttype.causer", Map.of(
                            PlaceHolderHandler.PLAYER_PLACEHOLDER, causer,
                            PlaceHolderHandler.POTION_EFFECT_PLACEHOLDER, potionEffect
                    ));
                });

            }
            case ALL -> {
                Collection<PotionEffect> appliedPotionEffects = applyPotionEffects(ChallengeManager.getInstance().getPlayers().stream().map(Bukkit::getPlayer).toList(), getCommon().getHowManyEffects());
                appliedPotionEffects.forEach(potionEffect -> {
                    ChatLogger.log("randompotioneffect.affecttype.all", Map.of(
                            PlaceHolderHandler.PLAYER_PLACEHOLDER, causer,
                            PlaceHolderHandler.POTION_EFFECT_PLACEHOLDER, potionEffect
                    ));
                });

            }
        }
    }

    private Collection<PotionEffect> applyPotionEffects(Collection<Player> players, int howMany) {
        List<PotionEffectType> types = new ArrayList<>(List.of(PotionEffectType.values()));
        List<PotionEffect> addedPotionEffects = new ArrayList<>();
        for(int i=0; i<howMany; i++) {
            PotionEffectType randomEffectType = types.remove(ThreadLocalRandom.current().nextInt(types.size()));
            int amplifier = ThreadLocalRandom.current().nextInt(5); //no potion effect goes beyond amplifier 5?
            PotionEffect randomPotionEffect = randomEffectType.createEffect(getCommon().getEffectDuration(), amplifier);
            addedPotionEffects.add(randomPotionEffect);
            players.forEach(player -> player.addPotionEffect(randomPotionEffect));
        }
        return addedPotionEffects;
    }

    @Override
    public RandomEffectPunishmentCommon getCommon() {
        return (RandomEffectPunishmentCommon) super.getCommon();
    }
}
