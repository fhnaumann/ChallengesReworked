package wand555.github.io.challengesreworked.goals.mob;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import wand555.github.io.challengesreworked.Collect;
import wand555.github.io.challengesreworked.Mapper;
import wand555.github.io.challengesreworked.goals.GoalCommon;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MobGoalCommon extends GoalCommon {

    private Map<EntityType, Collect> toKill;

    public MobGoalCommon() {
        this(false, new HashMap<>(Map.of(EntityType.ENDER_DRAGON, new Collect(1))));
    }

    public MobGoalCommon(Map<EntityType, Collect> toKill) {
        this(false, toKill);
    }

    public MobGoalCommon(boolean complete, Map<EntityType, Collect> toKill) {
        super(complete);
        this.toKill = toKill;
    }

    public Map<EntityType, Collect> getToKill() {
        return toKill;
    }

    public void setToKill(Map<EntityType, Collect> toKill) {
        this.toKill = toKill;
    }

    public static final TypeAdapter<MobGoalCommon> adapter = new TypeAdapter<>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull MobGoalCommon mobGoal) {
            return Map.of(
                    "complete", mobGoal.isComplete(),
                    "toKill", Mapper.fromEnumKeyMapToFileMap(mobGoal.getToKill())
            );
        }

        @NotNull
        @Override
        public MobGoalCommon deserialize(@NotNull Map<Object, Object> map) {
            return new MobGoalCommon(
                    (boolean) map.get("complete"),
                    Mapper.fromFileMapToEnumKeyMap((Map<?, ?>) map.get("toKill"), EntityType.class, Collect.class));
        }
    };

    @Override
    public MobGoalCommon copy() {
        return new MobGoalCommon(isComplete(), getToKill());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MobGoalCommon that = (MobGoalCommon) o;
        return toKill.equals(that.toKill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), toKill);
    }

    @Override
    public String toString() {
        return "MobGoalCommon{" +
                "toKill=" + toKill +
                ", adapter=" + adapter +
                '}';
    }
}
