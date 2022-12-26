package wand555.github.io.challengesreworked;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;

public interface Common {
    <T> TypeAdapter<T> getAdapter();
}
