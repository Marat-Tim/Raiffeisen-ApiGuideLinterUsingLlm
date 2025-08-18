package io.github.marattim.raif_api_guide.common;

import java.time.Duration;
import java.util.function.Supplier;

public class Timed<T1> implements Scalar<Timed.Result<T1>> {
    private final Supplier<T1> supplier;

    public Timed(Supplier<T1> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Result<T1> value() {
        var start = System.nanoTime();
        var value = supplier.get();
        var end = System.nanoTime();
        return new Result<>(value, Duration.ofNanos(end - start));
    }

    public record Result<T2>(T2 result, Duration duration) {
    }
}
