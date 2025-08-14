package io.github.marattim.raif_api_guide.llm_impl.part;

import io.github.marattim.raif_api_guide.SpecPart;

import java.nio.file.Path;

public record SimpleSpecPart(String text, int line, Path path) implements SpecPart {
}
