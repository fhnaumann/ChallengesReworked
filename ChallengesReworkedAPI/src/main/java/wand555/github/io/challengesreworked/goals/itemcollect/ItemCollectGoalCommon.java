package wand555.github.io.challengesreworked.goals.itemcollect;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class ItemCollectGoalCommon extends GoalCommon {

    private final Map<Material, Collect> toCollect;

    public ItemCollectGoalCommon(boolean complete, Map<Material, Collect> toCollect) {
        super(complete);
        this.toCollect = toCollect;
        StandardSerializer.getDefault().register(ItemCollectGoalCommon.class, adapter);
    }

    public Map<Material, Collect> getToCollect() {
        return toCollect;
    }

    private final TypeAdapter<ItemCollectGoalCommon> adapter = new TypeAdapter<ItemCollectGoalCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull ItemCollectGoalCommon itemCollectGoal) {
            return Map.of(
                    "complete", isComplete(),
                    "toCollect", toCollect.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue))
            );
        }

        @NotNull
        @Override
        public ItemCollectGoalCommon deserialize(@NotNull Map<Object, Object> map) {
            return new ItemCollectGoalCommon((boolean) map.get("complete"), ((Map<?, ?>) map.get("toCollect")).entrySet().stream()
                    .collect(Collectors.toMap(entry -> Material.valueOf(entry.getKey().toString()), entry -> (Collect) entry.getValue())));
        }
    };

    @Override
    public TypeAdapter<ItemCollectGoalCommon> getAdapter() {
        return adapter;
    }
}
