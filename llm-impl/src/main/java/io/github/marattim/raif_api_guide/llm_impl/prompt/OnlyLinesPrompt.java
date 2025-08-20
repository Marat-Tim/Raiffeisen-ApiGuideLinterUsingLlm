package io.github.marattim.raif_api_guide.llm_impl.prompt;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * Промпт, который просит llm искать только номера строк с ошибками
 */
public class OnlyLinesPrompt implements Prompt {
    @Override
    public String text(Rule rule) {
        return """
            У меня есть правило оформления open api.
            Найди в переданной части спецификации нарушения правила.
            Для каждой найденной ошибки на новой строке пиши номер строки и через запятую объяснение почему это ошибка.
            Например:
            3,потому что getAll не в kebab-case
            Не пиши в объяснении id ошибки.
            Если ошибок нет, то напиши одну цифру - 0.
            Больше ничего писать не нужно.
            ОЧЕНЬ ВАЖНО: Работай строго в соответствии с правилом.
            Если ошибка напрямую не относится к правилу, то не добавляй строку.
            Даже если ошибка в синтаксисе openapi
            Правило: %s
            """.formatted(rule.text());
    }

    @Override
    public String transformedMessage(String message) {
        return new TextWithNumberedLines(message).value();
    }

    @Override
    public Collection<? extends Defect> parsed(String response, SpecPart part, Rule rule) {
        if (response.trim().equals("0")) {
            return Collections.emptyList();
        }
        return response.lines()
            .map(line -> line.split(","))
            .map(
                split -> {
                    int line = Integer.parseInt(split[0].trim());
                    return new SimpleDefect(
                        new FullLineSelection(
                            line + part.line(),
                            part.text().lines().toList().get(line - 1)
                        ),
                        rule.id(),
                        rule.severity(),
                        split[1].trim(),
                        part.path(),
                        Stream.empty()
                    );
                }
            )
            .toList();
    }
}
