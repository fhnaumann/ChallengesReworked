package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Collect {
    private final int amountNeeded;
    private int currentAmount;

    public Collect(int amountNeeded) {
        this(amountNeeded, 0);
    }

    public Collect(int amountNeeded, int currentAmount) {
        this.amountNeeded = amountNeeded;
        this.currentAmount = currentAmount;
        StandardSerializer.getDefault().register(Collect.class, adapter);
    }

    public int getAmountNeeded() {
        return amountNeeded;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public boolean reached() {
        return currentAmount >= amountNeeded;
    }

    private final TypeAdapter<Collect> adapter = new TypeAdapter<Collect>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull Collect collect) {
            return Map.of(
                    "amountNeeded", amountNeeded,
                    "currentAmount", currentAmount
            );
        }

        @NotNull
        @Override
        public Collect deserialize(@NotNull Map<Object, Object> map) {
            return new Collect((int) map.get("amountNeeded"), (int) map.get("currentAmount"));
        }
    };
}
