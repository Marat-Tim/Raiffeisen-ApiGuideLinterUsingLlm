package io.github.marattim.raif_api_guide.common;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamFromIterator<T> implements Streamable<T> {
    private final Iterator<T> iterator;

    public StreamFromIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(
                iterator,
                0
            ),
            false
        );
    }
}
