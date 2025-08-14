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
            Для найденных ошибок напиши через запятую номера строк без пробелов.
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
        if (response.equals("0")) {
            return Collections.emptyList();
        }
        return Arrays.stream(response.split(","))
            .map(Integer::parseInt)
            .map(
                line -> new SimpleDefect(
                    // TODO сделать выделение конкретных ошибок
                    new FullLineSelection(
                        line + part.line(),
                        part.text().lines().toList().get(line - 1)
                    ),
                    rule.id(),
                    rule.severity(),
                    "https://github.com/Raiffeisen-DGTL/rest-api-guide/blob/main/rules/rules.md",
                    part.path(),
                    // TODO добавить авто исправления
                    Stream.empty()
                )
            )
            .toList();
    }
}
