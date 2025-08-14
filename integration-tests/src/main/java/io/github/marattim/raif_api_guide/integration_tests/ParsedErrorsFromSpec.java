package io.github.marattim.raif_api_guide.integration_tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

/**
 * Список ошибок в yaml openapi спецификации,
 * которые были помечены специальным комментарием.
 * Комментарии должны иметь формат @defects[...].
 * Внутри квадратных скобочек должны быть перечислены через
 * запятую id ошибок, которые есть на этой строке.
 * Если предполагается, что ошибка может быть между двумя
 * определенными строками, то нужно придумать уникальный
 * номер ошибки(чтобы для ошибок с одним id можно было указывать
 * несколько диапазонов), далее на первой строке указать
 * ... # @defects[(id ошибки):(номер ошибки):start], а на последней
 * ... # @defects[(id ошибки):(номер ошибки):end].
 * Примеры смотри в тестах
 */
public class ParsedErrorsFromSpec {
    private final String yaml;

    public ParsedErrorsFromSpec() {
        this(Path.of("./integration-tests/src/main/resources/example.yaml"));
    }

    public ParsedErrorsFromSpec(Path path) {
        this(safeReadString(path));
    }

    public ParsedErrorsFromSpec(String yaml) {
        this.yaml = yaml;
    }

    public Stream<ParsedDefectArea> stream() {
        Deque<OneParsedDefect> defects = new ArrayDeque<>(
            new ParsedDefectsInSquareBrackets(yaml).stream().toList()
        );
        List<ParsedDefectArea> result = new ArrayList<>();
        while (!defects.isEmpty()) {
            OneParsedDefect defect = defects.poll();
            if (defect.number().isEmpty()) {
                result.add(
                    new SimpleParsedDefectArea(
                        new LinesRangeWithOneLine(defect.line()),
                        defect.id(),
                        defect.number()
                    )
                );
            } else {
                OneParsedDefect same = null;
                for (OneParsedDefect other : defects) {
                    if (other.number().equals(defect.number()) && other.id().equals(defect.id())) {
                        same = other;
                        break;
                    }
                }
                result.add(
                    new SimpleParsedDefectArea(
                        new LinesRangeBetweenTwoLines(
                            Math.min(defect.line(), same.line()),
                            Math.max(defect.line(), same.line())
                        ),
                        defect.id(),
                        defect.number()
                    )
                );
                defects.remove(same);
            }
        }
        return result.stream();
    }

    private static String safeReadString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
