package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.Selection;
import io.github.marattim.raif_api_guide.Severity;
import io.github.marattim.raif_api_guide.Suggestion;

import java.nio.file.Path;
import java.util.stream.Stream;

record SimpleDefect(
    Selection selection,
    String id,
    Severity severity,
    String description,
    Path path,
    Stream<Suggestion> suggestions
) implements Defect {
}
