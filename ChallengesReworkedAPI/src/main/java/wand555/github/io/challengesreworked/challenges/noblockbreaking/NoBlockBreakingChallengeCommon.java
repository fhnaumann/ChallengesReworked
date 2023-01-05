package wand555.github.io.challengesreworked.challenges.noblockbreaking;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.Mapper;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.*;

public class NoBlockBreakingChallengeCommon extends PunishableChallengeCommon {

    private Set<Material> allowedToBreak;

    public NoBlockBreakingChallengeCommon() {
        this(Set.of(), Set.of());
    }

    public NoBlockBreakingChallengeCommon(Collection<PunishmentCommon> punishmentCommons, Set<Material> allowedToBreak) {
        super(punishmentCommons);
        this.allowedToBreak = allowedToBreak;
    }

    @Override
    public NoBlockBreakingChallengeCommon copy() {
        return new NoBlockBreakingChallengeCommon(copyPunishmentCommons(), getAllowedToBreak());
    }

    public Set<Material> getAllowedToBreak() {
        return allowedToBreak;
    }

    public void setAllowedToBreak(Set<Material> allowedToBreak) {
        this.allowedToBreak = allowedToBreak;
    }

    public static final TypeAdapter<NoBlockBreakingChallengeCommon> adapter = new TypeAdapter<NoBlockBreakingChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoBlockBreakingChallengeCommon noBlockBreakingChallengeCommon) {
            return Map.of(
                    "punishments", new ArrayList<>(noBlockBreakingChallengeCommon.getPunishmentCommons()),
                    "allowedToBreak", Mapper.fromEnumSetToFileList(noBlockBreakingChallengeCommon.getAllowedToBreak())
            );
        }

        @NotNull
        @Override
        public NoBlockBreakingChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoBlockBreakingChallengeCommon(
                    (List<PunishmentCommon>) map.get("punishments"),
                    Mapper.fromFileListToEnumSet((List<?>) map.get("allowedToBreak"), Material.class)
            );
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NoBlockBreakingChallengeCommon that = (NoBlockBreakingChallengeCommon) o;
        return Objects.equals(allowedToBreak, that.allowedToBreak);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allowedToBreak);
    }

    @Override
    public String toString() {
        return "NoBlockBreakingChallengeCommon{" +
                "punishmentCommons=" + punishmentCommons +
                ", allowedToBreak=" + allowedToBreak +
                ", adapter=" + adapter +
                '}';
    }
}
