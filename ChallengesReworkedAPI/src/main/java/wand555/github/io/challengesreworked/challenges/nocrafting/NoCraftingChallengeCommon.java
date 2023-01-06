package wand555.github.io.challengesreworked.challenges.nocrafting;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Mapper;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class NoCraftingChallengeCommon extends PunishableChallengeCommon {

    private Set<Material> allowedToCraft;
    private Set<InventoryType> forbiddenToUse;

    public NoCraftingChallengeCommon() {
        this(
                new TreeSet<>(),
                Set.of(Material.ENDER_EYE),
                Set.of(InventoryType.WORKBENCH, InventoryType.CRAFTING)
        );
    }

    public NoCraftingChallengeCommon(Collection<PunishmentCommon> punishments, Set<Material> allowedToCraft, Set<InventoryType> forbiddenToUse) {
        super(punishments);
        this.allowedToCraft = allowedToCraft;
        this.forbiddenToUse = forbiddenToUse;
    }

    public Set<Material> getAllowedToCraft() {
        return allowedToCraft;
    }

    public void setAllowedToCraft(Set<Material> allowedToCraft) {
        this.allowedToCraft = allowedToCraft;
    }

    public Set<InventoryType> getForbiddenToUse() {
        return forbiddenToUse;
    }

    public void setForbiddenToUse(Set<InventoryType> forbiddenToUse) {
        this.forbiddenToUse = forbiddenToUse;
    }

    public static final TypeAdapter<NoCraftingChallengeCommon> adapter = new TypeAdapter<NoCraftingChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoCraftingChallengeCommon noCraftingChallenge) {
            Map<Object, Object> map = new HashMap<>();
            map.put("punishments", new ArrayList<>(noCraftingChallenge.getPunishmentCommons()));
            map.put("allowedToCraft", Mapper.fromEnumSetToFileList(noCraftingChallenge.getAllowedToCraft()));
            map.put("forbiddenToUse", Mapper.fromEnumSetToFileList(noCraftingChallenge.getForbiddenToUse()));
            return map;
        }

        @NotNull
        @Override
        public NoCraftingChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoCraftingChallengeCommon(
                    ((List<PunishmentCommon>) map.get("punishments")),
                    //((List<?>) map.get("allowedToCraft")).stream().map(o -> Material.valueOf(o.toString())).collect(Collectors.toSet()),
                    Mapper.fromFileListToEnumSet((List<?>) map.get("allowedToCraft"), Material.class),
                    Mapper.fromFileListToEnumSet((List<?>) map.get("forbiddenToUse"), InventoryType.class));
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NoCraftingChallengeCommon that = (NoCraftingChallengeCommon) o;
        return Objects.equals(allowedToCraft, that.allowedToCraft) && Objects.equals(forbiddenToUse, that.forbiddenToUse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allowedToCraft, forbiddenToUse);
    }

    @Override
    public NoCraftingChallengeCommon copy() {
        return new NoCraftingChallengeCommon(copyPunishmentCommons(), allowedToCraft, forbiddenToUse);
    }
}
