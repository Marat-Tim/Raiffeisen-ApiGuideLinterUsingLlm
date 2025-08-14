package io.github.marattim.raif_api_guide.integration_tests.fake;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.Selection;
import io.github.marattim.raif_api_guide.Severity;
import io.github.marattim.raif_api_guide.Suggestion;

import java.nio.file.Path;
import java.util.stream.Stream;

public class FakeDefect implements Defect {
    private final String id;

    public FakeDefect(String id) {
        this.id = id;
    }

    @Override
    public Selection selection() {
        return null;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Severity severity() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Path path() {
        return null;
    }

    @Override
    public Stream<? extends Suggestion> suggestions() {
        return Stream.empty();
    }
}
