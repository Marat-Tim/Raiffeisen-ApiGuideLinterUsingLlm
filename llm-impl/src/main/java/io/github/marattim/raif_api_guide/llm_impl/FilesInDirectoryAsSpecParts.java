package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.SpecPart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Собирает части спецификации из файлов в указанной папке, проходясь по ней рекурсивно
 */
class FilesInDirectoryAsSpecParts {
    private final Path dir;

    FilesInDirectoryAsSpecParts(Path dir) {
        this.dir = dir;
    }

    /**
     * Возвращает список абсолютных путей к файлам в папке(рекурсивно)
     */
    Stream<? extends SpecPart> stream() {
        try (var stream = Files.walk(dir)) {
            return stream.filter(Files::isRegularFile)
                .map(file -> {
                    try {
                        return new SmallSpecPart(file.toAbsolutePath(), Files.readString(file));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList()
                .stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
