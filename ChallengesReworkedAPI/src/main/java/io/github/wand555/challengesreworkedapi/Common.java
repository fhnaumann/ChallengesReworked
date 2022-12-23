package io.github.wand555.challengesreworkedapi;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;

public interface Common {
    <T> TypeAdapter<T> getAdapter();
}
