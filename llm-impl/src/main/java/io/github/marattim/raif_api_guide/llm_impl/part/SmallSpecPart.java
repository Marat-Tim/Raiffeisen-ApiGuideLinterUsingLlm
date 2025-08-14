package io.github.marattim.raif_api_guide.llm_impl.part;

import io.github.marattim.raif_api_guide.SpecPart;

import java.nio.file.Path;

/**
 * Небольшая часть спецификации в одном документе,
 * который полностью поместится в запрос модели.
 * Предполагается использование для спецификаций,
 * разбитых по отдельным файлам
 */
public class SmallSpecPart implements SpecPart {
    private final Path path;
    private final String content;

    public SmallSpecPart(Path path, String content) {
        this.path = path;
        this.content = content;
    }

    @Override
    public String text() {
        return content;
    }

    @Override
    public int line() {
        return 0;
    }

    @Override
    public Path path() {
        return path;
    }
}
