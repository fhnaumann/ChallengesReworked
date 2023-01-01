package wand555.github.io.challengesreworked.challenges.noblockbreaking;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.challenges.noblockplacing.NoBlockPlacingChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.*;
import java.util.stream.Collectors;

public class NoBlockBreakingChallengeCommon extends PunishableChallengeCommon {

    private Set<Material> allowedToBreak;

    public NoBlockBreakingChallengeCommon() {
        this(Set.of(), Set.of());
    }

    public NoBlockBreakingChallengeCommon(Collection<PunishmentCommon> punishmentCommons, Set<Material> allowedToBreak) {
        super(punishmentCommons);
        this.allowedToBreak = allowedToBreak;
        StandardSerializer.getDefault().register(NoBlockBreakingChallengeCommon.class, adapter);
    }

    @Override
    public NoBlockBreakingChallengeCommon copy() {
        return new NoBlockBreakingChallengeCommon(getPunishmentCommons(), getAllowedToBreak());
    }

    public Set<Material> getAllowedToBreak() {
        return allowedToBreak;
    }

    public void setAllowedToBreak(Set<Material> allowedToBreak) {
        this.allowedToBreak = allowedToBreak;
    }

    private final TypeAdapter<NoBlockBreakingChallengeCommon> adapter = new TypeAdapter<NoBlockBreakingChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoBlockBreakingChallengeCommon noBlockBreakingChallengeCommon) {
            return Map.of(
                    "punishments", new ArrayList<>(getPunishmentCommons()),
                    "allowedToBreak", getAllowedToBreak().stream().map(Enum::toString).toList()
            );
        }

        @NotNull
        @Override
        public NoBlockBreakingChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoBlockBreakingChallengeCommon(
                    (List<PunishmentCommon>) map.get("punishments"),
                    ((List<?>) map.get("allowedToBreak")).stream().map(o -> Material.valueOf(o.toString())).collect(Collectors.toSet())
            );
        }
    };
}
