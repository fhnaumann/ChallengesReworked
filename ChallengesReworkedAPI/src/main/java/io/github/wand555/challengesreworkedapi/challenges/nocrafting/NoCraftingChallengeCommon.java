package io.github.wand555.challengesreworkedapi.challenges.nocrafting;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import io.github.wand555.challengesreworkedapi.Commonable;
import io.github.wand555.challengesreworkedapi.challenges.PunishableChallengeCommon;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NoCraftingChallengeCommon extends PunishableChallengeCommon {

    private final Set<Material> allowedToCraft;
    private final Set<InventoryType> forbiddenToUse;

    public NoCraftingChallengeCommon() {
        this(
                List.of(),
                Set.of(Material.ENDER_EYE),
                Set.of(InventoryType.WORKBENCH, InventoryType.CRAFTING)
        );
    }

    public NoCraftingChallengeCommon(List<Punishment> punishments, Set<Material> allowedToCraft, Set<InventoryType> forbiddenToUse) {
        super(punishments);
        this.allowedToCraft = allowedToCraft;
        this.forbiddenToUse = forbiddenToUse;
        StandardSerializer.getDefault().register(NoCraftingChallengeCommon.class, adapter);
    }

    public Set<Material> getAllowedToCraft() {
        return allowedToCraft;
    }

    public Set<InventoryType> getForbiddenToUse() {
        return forbiddenToUse;
    }

    public final TypeAdapter<NoCraftingChallengeCommon> adapter = new TypeAdapter<NoCraftingChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull NoCraftingChallengeCommon noCraftingChallenge) {
            Map<Object, Object> map = new HashMap<>();
            map.put("punishments", new ArrayList<>(getPunishmentCommons()));
            map.put("allowedToCraft", allowedToCraft.stream().map(Enum::toString).collect(Collectors.toList()));
            map.put("forbiddenToUse", forbiddenToUse.stream().map(Enum::toString).collect(Collectors.toList()));
            return map;
        }

        @NotNull
        @Override
        public NoCraftingChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new NoCraftingChallengeCommon(
                    (List<Punishment>) map.get("punishments"),
                    ((List<?>) map.get("allowedToCraft")).stream().map(o -> Material.valueOf(o.toString())).collect(Collectors.toSet()),
                    ((List<?>) map.get("forbiddenToUse")).stream().map(o -> InventoryType.valueOf(o.toString())).collect(Collectors.toSet()));
        }
    };

    @Override
    public TypeAdapter<NoCraftingChallengeCommon> getAdapter() {
        return adapter;
    }
}
