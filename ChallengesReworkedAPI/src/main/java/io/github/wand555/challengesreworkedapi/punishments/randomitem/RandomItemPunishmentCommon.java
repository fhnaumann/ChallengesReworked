package io.github.wand555.challengesreworkedapi.punishments.randomitem;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import io.github.wand555.challengesreworkedapi.punishments.AffectType;
import io.github.wand555.challengesreworkedapi.punishments.PunishmentCommon;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RandomItemPunishmentCommon extends PunishmentCommon {

    private final int howManyRemoved;

    public RandomItemPunishmentCommon(int amountTriggered, AffectType affectType, int howManyRemoved) {
        super(amountTriggered, affectType);
        this.howManyRemoved = howManyRemoved;
    }

    public final TypeAdapter<RandomItemPunishmentCommon> typeAdapter = new TypeAdapter<RandomItemPunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull RandomItemPunishmentCommon randomItemPunishment) {
            return Map.of(
                    "amountTriggered", getAmountTriggered(),
                    "affectType", getAffectType().toString(),
                    "howManyRemoved", howManyRemoved
            );
        }

        @NotNull
        @Override
        public RandomItemPunishmentCommon deserialize(@NotNull Map<Object, Object> map) {
            return new RandomItemPunishmentCommon(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString()),
                    (int) map.get("howManyRemoved")
            );
        }
    };

    @Override
    public TypeAdapter<RandomItemPunishmentCommon> getTypeAdapter() {
        return typeAdapter;
    }
}
