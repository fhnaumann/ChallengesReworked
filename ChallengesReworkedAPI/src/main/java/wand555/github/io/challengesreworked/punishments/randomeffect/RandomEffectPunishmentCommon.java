package wand555.github.io.challengesreworked.punishments.randomeffect;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;

import java.util.Map;
import java.util.Objects;

public class RandomEffectPunishmentCommon extends PunishmentCommon {

    private int howManyEffects;
    private int effectDuration; //in seconds

    public RandomEffectPunishmentCommon() {
        this(0, AffectType.ALL, 1, 5);
    }

    public RandomEffectPunishmentCommon(int amountTriggered, AffectType affectType, int howManyEffects, int effectDuration) {
        super(amountTriggered, affectType);
        this.howManyEffects = howManyEffects;
        this.effectDuration = effectDuration;
    }

    public int getHowManyEffects() {
        return howManyEffects;
    }

    public void setHowManyEffects(int howManyEffects) {
        this.howManyEffects = howManyEffects;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public static final TypeAdapter<RandomEffectPunishmentCommon> adapter = new TypeAdapter<RandomEffectPunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull RandomEffectPunishmentCommon randomEffectPunishmentCommon) {
            return Map.of(
                    "amountTriggered", randomEffectPunishmentCommon.getAmountTriggered(),
                    "affectType", randomEffectPunishmentCommon.getAffectType().toString(),
                    "howManyEffects", randomEffectPunishmentCommon.getHowManyEffects(),
                    "effectDuration", randomEffectPunishmentCommon.getEffectDuration()
            );
        }

        @NotNull
        @Override
        public RandomEffectPunishmentCommon deserialize(@NotNull Map<Object, Object> map) {
            return new RandomEffectPunishmentCommon(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString()),
                    (int) map.get("howManyRemoved"),
                    (int) map.get("effectDuration")
            );
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RandomEffectPunishmentCommon that = (RandomEffectPunishmentCommon) o;
        return howManyEffects == that.howManyEffects && effectDuration == that.effectDuration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), howManyEffects, effectDuration);
    }

    @Override
    public String toString() {
        return "RandomEffectPunishmentCommon{" +
                "affectType=" + affectType +
                ", howManyEffects=" + howManyEffects +
                ", effectDuration=" + effectDuration +
                ", adapter=" + adapter +
                '}';
    }
}
