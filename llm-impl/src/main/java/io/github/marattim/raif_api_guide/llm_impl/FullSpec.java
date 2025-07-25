package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.SpecPart;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Полная спецификация open api
 */
public class FullSpec {
    private final Stream<? extends SpecPart> parts;

    /**
     * Создаёт спецификацию из файлов в директории
     * (проходит рекурсивно по всем папкам и собирает все файлы)
     */
    public FullSpec(Path dir) {
        this(new FilesInDirectoryAsSpecParts(dir).stream());
    }

    /**
     * Создает спецификацию из yaml текста и абсолютного пути к файлу,
     * который содержит данный yaml
     */
    public FullSpec(String yaml, Path path, int factor) {
        this(new YamlElements(path, yaml, factor).stream());
    }

    public FullSpec(Stream<? extends SpecPart> parts) {
        this.parts = parts;
    }

    public Stream<? extends SpecPart> parts() {
        return parts;
    }
}
