package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.SpecPart;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Разбиение yaml документа на части
 * <p>
 * Каждая часть содержит factor строк
 */
public class YamlElements {
    private final Path path;
    private final String yaml;
    private final int factor;

    public YamlElements(Path path, String yaml, int factor) {
        this.path = path;
        this.yaml = yaml;
        this.factor = factor;
    }

    public Stream<SpecPart> stream() {
        List<String> lines = yaml.lines().toList();
        ArrayList<SpecPart> result = new ArrayList<>();
        int i;
        for (i = 0; i < lines.size() / factor; i++) {
            result.add(
                new SimpleSpecPart(
                    String.join("\n", lines.subList(i * factor, (i + 1) * factor)),
                    i * factor,
                    path
                )
            );
        }
        result.add(
            new SimpleSpecPart(
                String.join("\n", lines.subList(i * factor, lines.size())),
                i * factor,
                path
            )
        );
        return result.stream();
    }
}
