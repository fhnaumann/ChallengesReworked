package wand555.github.io.challengesreworked.punishments.health;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class HealthPunishmentCommon extends PunishmentCommon {

    private final int healthAmount;

    public HealthPunishmentCommon(int amountTriggered, AffectType affectType, int healthAmount) {
        super(amountTriggered, affectType);
        this.healthAmount = healthAmount;
        StandardSerializer.getDefault().register(HealthPunishmentCommon.class, adapter);
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public final TypeAdapter<HealthPunishmentCommon> adapter = new TypeAdapter<HealthPunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull HealthPunishmentCommon healthPunishment) {
            return Map.of(
                    "amountTriggered", getAmountTriggered(),
                    "affectType", getAffectType().toString(),
                    "healthAmount", healthAmount
            );
        }

        @NotNull
        @Override
        public HealthPunishmentCommon deserialize(@NotNull Map<Object, Object> map) {
            return new HealthPunishmentCommon(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString()),
                    (int) map.get("healthAmount")
            );
        }
    };

    @Override
    public TypeAdapter<HealthPunishmentCommon> getAdapter() {
        return adapter;
    }
}
