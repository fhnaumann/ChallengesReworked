package io.github.wand555.challengesreworkedapi.goals;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import io.github.wand555.challengesreworkedapi.Common;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class GoalCommon implements Common {

    private boolean complete;

    protected GoalCommon(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
