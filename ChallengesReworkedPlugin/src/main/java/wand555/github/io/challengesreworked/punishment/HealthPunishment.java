package wand555.github.io.challengesreworked.punishment;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.ChallengeManager;
import wand555.github.io.challengesreworked.LanguageHandler;
import wand555.github.io.challengesreworked.logging.ChatLogger;
import wand555.github.io.challengesreworked.logging.PlaceHolderHandler;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class HealthPunishment extends Punishment {

    private final int healthAmount;

    public HealthPunishment(int amountTriggered, AffectType affectType, int healthAmount) {
        super(amountTriggered, affectType);
        this.healthAmount = healthAmount;
        StandardSerializer.getDefault().register(HealthPunishment.class, typeAdapter);
    }


    @Override
    public void enforcePunishment(Player causer) {
        super.enforcePunishment(causer);
        switch(affectType) {
            case CAUSER -> {
                damagePlayer(causer);
                ChatLogger.log("punishments.health.affecttype.causer",
                        Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, causer,
                                PlaceHolderHandler.AMOUNT_PLACEHOLDER, healthAmount
                        ));
            }
            case ALL -> {
                ChallengeManager.getInstance().getPlayers().stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(this::damagePlayer);
                ChatLogger.log("punishments.health.affecttype.all",
                        Map.of(PlaceHolderHandler.AMOUNT_PLACEHOLDER, healthAmount));
            }
        }
    }

    private void damagePlayer(Player player) {
        player.damage(healthAmount);
    }

    private final TypeAdapter<HealthPunishment> typeAdapter = new TypeAdapter<HealthPunishment>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull HealthPunishment healthPunishment) {
            return Map.of(
                    "amountTriggered", getAmountTriggered(),
                    "affectType", getAffectType().toString(),
                    "healthAmount", healthAmount
            );
        }

        @NotNull
        @Override
        public HealthPunishment deserialize(@NotNull Map<Object, Object> map) {
            return new HealthPunishment(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString()),
                    (int) map.get("healthAmount")
            );
        }
    };
}
