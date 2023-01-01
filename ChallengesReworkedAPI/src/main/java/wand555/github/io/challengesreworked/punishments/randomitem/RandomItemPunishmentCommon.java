package wand555.github.io.challengesreworked.punishments.randomitem;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RandomItemPunishmentCommon extends PunishmentCommon {

    private int howManyRemoved;

    public RandomItemPunishmentCommon() {
        this(0, AffectType.ALL, 1);
    }

    public RandomItemPunishmentCommon(int amountTriggered, AffectType affectType, int howManyRemoved) {
        super(amountTriggered, affectType);
        this.howManyRemoved = howManyRemoved;
        StandardSerializer.getDefault().register(RandomItemPunishmentCommon.class, adapter);
    }

    public final TypeAdapter<RandomItemPunishmentCommon> adapter = new TypeAdapter<RandomItemPunishmentCommon>() {
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

    public int getHowManyRemoved() {
        return howManyRemoved;
    }

    public void setHowManyRemoved(int howManyRemoved) {
        this.howManyRemoved = howManyRemoved;
    }

    @Override
    public RandomItemPunishmentCommon copy() {
        return new RandomItemPunishmentCommon(getAmountTriggered(), getAffectType(), getHowManyRemoved());
    }
}
