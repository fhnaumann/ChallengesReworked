package wand555.github.io.challengesreworked.goals.itemcollect;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemCollectGoalCommon extends GoalCommon {

    private Map<Material, Collect> toCollect;

    public ItemCollectGoalCommon() {
        this(false, new HashMap<>());
    }

    public ItemCollectGoalCommon(boolean complete, Map<Material, Collect> toCollect) {
        super(complete);
        this.toCollect = toCollect;
    }

    public Map<Material, Collect> getToCollect() {
        return toCollect;
    }

    public void setToCollect(Map<Material, Collect> toCollect) {
        this.toCollect = toCollect;
    }

    public static final TypeAdapter<ItemCollectGoalCommon> adapter = new TypeAdapter<ItemCollectGoalCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull ItemCollectGoalCommon itemCollectGoal) {
            return Map.of(
                    "complete", itemCollectGoal.isComplete(),
                    "toCollect", itemCollectGoal.getToCollect().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue))
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
    public ItemCollectGoalCommon copy() {
        return new ItemCollectGoalCommon(isComplete(), getToCollect());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ItemCollectGoalCommon that = (ItemCollectGoalCommon) o;
        return Objects.equals(toCollect, that.toCollect) && Objects.equals(adapter, that.adapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), toCollect, adapter);
    }

    @Override
    public String toString() {
        return "ItemCollectGoalCommon{" +
                "toCollect=" + toCollect +
                ", adapter=" + adapter +
                '}';
    }
}
