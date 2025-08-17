package io.github.marattim.raif_api_guide.common;

import java.util.stream.Stream;

public interface Streamable<T> {
    Stream<T> stream();
}
