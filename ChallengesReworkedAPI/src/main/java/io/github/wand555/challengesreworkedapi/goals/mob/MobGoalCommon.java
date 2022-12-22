package io.github.wand555.challengesreworkedapi.goals.mob;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import io.github.wand555.challengesreworkedapi.Collect;
import io.github.wand555.challengesreworkedapi.goals.GoalCommon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class MobGoalCommon extends GoalCommon {

    private final Map<EntityType, Collect> toKill;

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

    private final TypeAdapter<MobGoalCommon> adapter = new TypeAdapter<>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull MobGoalCommon mobGoal) {
            return Map.of(
                    "complete", isComplete(),
                    "toKill", mobGoal.getToKill().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> {
                        System.out.println(entry.getValue().getCurrentAmount());
                        //Map.Entry::getValue

                        return entry.getValue();
                    }))
            );
        }

        @NotNull
        @Override
        public MobGoalCommon deserialize(@NotNull Map<Object, Object> map) {
            return new MobGoalCommon(
                    (boolean) map.get("complete"),
                    ((Map<?, ?>) map.get("toKill")).entrySet().stream().collect(Collectors.toMap(
                            entry -> EntityType.valueOf(entry.getKey().toString()), entry -> (Collect) entry.getValue()
                    )));
        }
    };
    @Override
    public TypeAdapter<MobGoalCommon> getTypeAdapter() {
        return adapter;
    }
}
