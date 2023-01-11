package wand555.github.io.challengesreworked.punishments.endchallenge;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.punishments.AffectType;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.death.DeathPunishmentCommon;

import java.util.Map;

public class EndChallengePunishmentCommon extends PunishmentCommon {

    public EndChallengePunishmentCommon() {
        this(0);
    }

    public EndChallengePunishmentCommon(int amountTriggered) {
        super(amountTriggered, AffectType.ALL);
    }

    @Override
    public EndChallengePunishmentCommon copy() {
        return new EndChallengePunishmentCommon(getAmountTriggered());
    }

    public static final TypeAdapter<EndChallengePunishmentCommon> adapter = new TypeAdapter<EndChallengePunishmentCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull EndChallengePunishmentCommon endChallengePunishmentCommon) {
            return Map.of(
                    "amountTriggered", endChallengePunishmentCommon.getAmountTriggered()
            );
        }

        @NotNull
        @Override
        public EndChallengePunishmentCommon deserialize(@NotNull Map<Object, Object> map) {
            return new EndChallengePunishmentCommon(
                    (int) map.get("amountTriggered")
            );
        }
    };
}
