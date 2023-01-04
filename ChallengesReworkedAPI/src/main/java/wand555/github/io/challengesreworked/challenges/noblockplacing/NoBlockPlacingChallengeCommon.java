package wand555.github.io.challengesreworked.challenges.noblockplacing;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.*;
import java.util.stream.Collectors;

public class NoBlockPlacingChallengeCommon extends PunishableChallengeCommon {

    private Set<Material> allowedToPlace;

    public NoBlockPlacingChallengeCommon() {
        this(
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public NoBlockPlacingChallengeCommon(Collection<PunishmentCommon> punishmentCommons, Set<Material> allowedToPlace) {
        super(punishmentCommons);
        this.allowedToPlace = allowedToPlace;
    }

    public Set<Material> getAllowedToPlace() {
        return allowedToPlace;
    }

    public void setAllowedToPlace(Set<Material> allowedToPlace) {
        this.allowedToPlace = allowedToPlace;
    }

    @Override
    public NoBlockPlacingChallengeCommon copy() {
        return new NoBlockPlacingChallengeCommon(copyPunishmentCommons(), getAllowedToPlace());
    }

    public static final TypeAdapter<NoBlockPlacingChallengeCommon> adapter = new TypeAdapter<NoBlockPlacingChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoBlockPlacingChallengeCommon common) {
            Map<Object, Object> map = new HashMap<>();
            map.put("punishments", new ArrayList<>(common.getPunishmentCommons()));
            map.put("allowedToPlace", common.getAllowedToPlace().stream().map(Enum::toString).toList());
            return map;
        }

        @NotNull
        @Override
        public NoBlockPlacingChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoBlockPlacingChallengeCommon(
                    (List<PunishmentCommon>) map.get("punishments"),
                    ((List<?>) map.get("allowedToPlace")).stream().map(o -> Material.valueOf(o.toString())).collect(Collectors.toSet())
            );
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NoBlockPlacingChallengeCommon that = (NoBlockPlacingChallengeCommon) o;
        return Objects.equals(allowedToPlace, that.allowedToPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allowedToPlace);
    }

    @Override
    public String toString() {
        return "NoBlockPlacingChallengeCommon{" +
                "punishmentCommons=" + punishmentCommons +
                ", allowedToPlace=" + allowedToPlace +
                ", adapter=" + adapter +
                '}';
    }
}
