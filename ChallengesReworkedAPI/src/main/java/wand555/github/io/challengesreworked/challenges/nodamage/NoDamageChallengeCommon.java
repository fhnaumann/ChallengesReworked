package wand555.github.io.challengesreworked.challenges.nodamage;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.*;

public class NoDamageChallengeCommon extends PunishableChallengeCommon {

        public NoDamageChallengeCommon() {
                this(new HashSet<>());
        }

        public NoDamageChallengeCommon(Collection<PunishmentCommon> punishmentCommons) {
                super(punishmentCommons);
                StandardSerializer.getDefault().register(NoDamageChallengeCommon.class, adapter);
        }

        @Override
        public NoDamageChallengeCommon copy() {
                return new NoDamageChallengeCommon(copyPunishmentCommons());
        }

        private final TypeAdapter<NoDamageChallengeCommon> adapter = new TypeAdapter<NoDamageChallengeCommon>() {
                @NotNull
                @Override
                public Map<Object, Object> serialize(@NotNull NoDamageChallengeCommon noDamageChallengeCommon) {
                        return Map.of(
                                "punishments", new ArrayList<>(getPunishmentCommons())
                        );
                }

                @NotNull
                @Override
                public NoDamageChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
                        return new NoDamageChallengeCommon(
                                (List<PunishmentCommon>) map.get("punishments")
                        );
                }
        };

        @Override
        public boolean equals(Object other) {
                return super.equals(other);
        }
}
