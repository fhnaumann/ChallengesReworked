package wand555.github.io.challengesreworked.challenges.nodeath;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import wand555.github.io.challengesreworked.punishments.endchallenge.EndChallengePunishmentCommon;

import java.util.*;

public class NoDeathChallengeCommon extends PunishableChallengeCommon {

    public NoDeathChallengeCommon() {
        this(new TreeSet<>(Set.of(new EndChallengePunishmentCommon())));
    }

    public NoDeathChallengeCommon(Collection<PunishmentCommon> punishmentCommons) {
        super(punishmentCommons);
    }

    @Override
    public ChallengeCommon copy() {
        return new NoDeathChallengeCommon(copyPunishmentCommons());
    }
    public static final TypeAdapter<NoDeathChallengeCommon> adapter = new TypeAdapter<NoDeathChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoDeathChallengeCommon noDeathChallengeCommon) {
            return Map.of(
                    "punishments", new ArrayList<>(noDeathChallengeCommon.getPunishmentCommons())
            );
        }

        @NotNull
        @Override
        public NoDeathChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoDeathChallengeCommon(
                    (List<PunishmentCommon>) map.get("punishments")
            );
        }
    };
}
