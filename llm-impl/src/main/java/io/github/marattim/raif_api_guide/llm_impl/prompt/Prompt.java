package io.github.marattim.raif_api_guide.llm_impl.prompt;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;

import java.util.Collection;

/**
 * Промпт, который передается llm и способ парсинга ответа при таком промпте
 */
public interface Prompt {
    /**
     * Текст, который будет передан llm в начале запроса
     */
    String text(Rule rule);

    /**
     * Преобразования строки, которая будет передана llm как пользовательское сообщение
     */
    String transformedMessage(String message);

    /**
     * Парсинг ответа llm в ошибки
     */
    Collection<? extends Defect> parsed(String response, SpecPart part, Rule rule);
}
