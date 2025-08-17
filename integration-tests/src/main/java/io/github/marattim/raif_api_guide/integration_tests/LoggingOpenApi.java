package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.common.StreamFromIterator;

import java.time.Duration;
import java.util.Iterator;
import java.util.stream.Stream;

public class LoggingOpenApi implements OpenApi {
    private final OpenApi origin;

    public LoggingOpenApi(OpenApi origin) {
        this.origin = origin;
    }

    @Override
    public Stream<Defect> defects() {
        Stream<? extends Defect> source = origin.defects();
        Iterator<? extends Defect> iterator = source.iterator();
        return new StreamFromIterator<Defect>(
            new Iterator<>() {
                @Override
                public boolean hasNext() {
                    long start = System.nanoTime();
                    System.out.println("Получаем очередной дефект");
                    boolean hn = iterator.hasNext();
                    long end = System.nanoTime();
                    System.out.println("Получили за " + Duration.ofNanos(end - start));
                    return hn;
                }

                @Override
                public Defect next() {
                    return iterator.next();
                }
            }
        ).stream().onClose(source::close);
    }


}
