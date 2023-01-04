package wand555.github.io.challengesreworked.punishments.health;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class HealthPunishmentCommon extends PunishmentCommon {

    private int healthAmount;

    public HealthPunishmentCommon() {
        this(0, AffectType.ALL, 1);
    }

    public HealthPunishmentCommon(int amountTriggered, AffectType affectType, int healthAmount) {
        super(amountTriggered, affectType);
        this.healthAmount = healthAmount;
        StandardSerializer.getDefault().register(HealthPunishmentCommon.class, adapter);
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
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
    public HealthPunishmentCommon copy() {
        return new HealthPunishmentCommon(getAmountTriggered(), getAffectType(), getHealthAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HealthPunishmentCommon that = (HealthPunishmentCommon) o;
        return healthAmount == that.healthAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), healthAmount);
    }

    @Override
    public String toString() {
        return "HealthPunishmentCommon{" +
                "affectType=" + affectType +
                ", healthAmount=" + healthAmount +
                ", adapter=" + adapter +
                '}';
    }
}
