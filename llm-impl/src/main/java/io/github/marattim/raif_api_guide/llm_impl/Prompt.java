package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.SpecPart;

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
     * Парсинг ответа llm в ошибки
     */
    Collection<? extends Defect> parsed(String response, SpecPart part, Rule rule);
}
