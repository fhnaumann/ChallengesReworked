package wand555.github.io.challengesreworked.punishments.randomeffect;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.randomitem.RandomItemPunishmentCommon;

import java.util.Map;

public class RandomEffectPunishmentCommon extends PunishmentCommon {

    private int howManyEffects;
    private int effectDuration; //in seconds

    public RandomEffectPunishmentCommon(int amountTriggered, AffectType affectType, int howManyEffects, int effectDuration) {
        super(amountTriggered, affectType);
        this.howManyEffects = howManyEffects;
        this.effectDuration = effectDuration;
        StandardSerializer.getDefault().register(RandomEffectPunishmentCommon.class, adapter);
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

    private final TypeAdapter<RandomEffectPunishmentCommon> adapter = new TypeAdapter<RandomEffectPunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull RandomEffectPunishmentCommon randomEffectPunishmentCommon) {
            return Map.of(
                    "amountTriggered", getAmountTriggered(),
                    "affectType", getAffectType().toString(),
                    "howManyEffects", howManyEffects,
                    "effectDuration", effectDuration
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
}
