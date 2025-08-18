package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.common.StreamFromIterator;
import io.github.marattim.raif_api_guide.common.Timed;

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
                    System.out.println("Получаем очередной дефект");
                    Timed.Result<Boolean> timed = new Timed<>(iterator::hasNext).value();
                    System.out.println("Получили за " + timed.duration());
                    return timed.result();
                }

                @Override
                public Defect next() {
                    return iterator.next();
                }
            }
        ).stream().onClose(source::close);
    }


}
