package wand555.github.io.challengesreworked.challenges.noregeneration;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;

import java.util.Map;

public class NoRegenerationChallengeCommon extends ChallengeCommon {

    // In a NoRegChallenge only regeneration through saturation is disabled.
    // If this flag is set to true, even golden apples/potions/suspicious stew won't regenerate health.
    private boolean disableAllRegeneration;

    public NoRegenerationChallengeCommon() {
        this(false);
    }

    public NoRegenerationChallengeCommon(boolean disableAllRegeneration) {
        this.disableAllRegeneration = disableAllRegeneration;
    }

    public boolean isDisableAllRegeneration() {
        return disableAllRegeneration;
    }

    public void setDisableAllRegeneration(boolean disableAllRegeneration) {
        this.disableAllRegeneration = disableAllRegeneration;
    }

    public static final TypeAdapter<NoRegenerationChallengeCommon> adapter = new TypeAdapter<NoRegenerationChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoRegenerationChallengeCommon noRegenerationChallengeCommon) {
            return Map.of(
                    "disableAllRegeneration", noRegenerationChallengeCommon.isDisableAllRegeneration()
            );
        }

        @NotNull
        @Override
        public NoRegenerationChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoRegenerationChallengeCommon(
                    (boolean) map.get("disableAllRegeneration")
            );
        }
    };

    @Override
    public NoRegenerationChallengeCommon copy() {
        return new NoRegenerationChallengeCommon(isDisableAllRegeneration());
    }
}
