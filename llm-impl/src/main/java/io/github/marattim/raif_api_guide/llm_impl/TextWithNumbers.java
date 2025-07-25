package io.github.marattim.raif_api_guide.llm_impl;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Текст в который для каждой строки добавлен номер строки
 * (для того, чтобы llm работал лучше)
 */
class TextWithNumbers {
    private final String text;

    TextWithNumbers(String text) {
        this.text = text;
    }

    String value() {
        var lines = text.lines().toList();
        return IntStream.range(0, lines.size())
            .mapToObj(i -> "%s: %s".formatted(i + 1, lines.get(i)))
            .collect(Collectors.joining("\n"));
    }
}
