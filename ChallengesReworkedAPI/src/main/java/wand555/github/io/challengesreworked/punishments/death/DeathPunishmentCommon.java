package wand555.github.io.challengesreworked.punishments.death;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.inventoryclear.InventoryClearPunishmentCommon;

import java.util.Map;

public class DeathPunishmentCommon extends PunishmentCommon {

    public DeathPunishmentCommon() {
        super();
    }

    public DeathPunishmentCommon(int amountTriggered, AffectType affectType) {
        super(amountTriggered, affectType);
    }

    @Override
    public DeathPunishmentCommon copy() {
        return new DeathPunishmentCommon(getAmountTriggered(), getAffectType());
    }

    public static final TypeAdapter<DeathPunishmentCommon> adapter = new TypeAdapter<DeathPunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull DeathPunishmentCommon deathPunishmentCommon) {
            return Map.of(
                    "amountTriggered", deathPunishmentCommon.getAmountTriggered(),
                    "affectType", deathPunishmentCommon.getAffectType().toString()
            );
        }

        @NotNull
        @Override
        public DeathPunishmentCommon deserialize(@NotNull Map<Object, Object> map) {
            return new DeathPunishmentCommon(
                    (int) map.get("amountTriggered"),
                    AffectType.valueOf(map.get("affectType").toString())
            );
        }
    };
}
