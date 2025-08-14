package io.github.marattim.raif_api_guide.llm_impl.rule;

import io.github.marattim.raif_api_guide.Severity;

/**
 * Одно правило гайда
 */
public interface Rule {
    /**
     * Имя правила
     */
    String id();

    /**
     * Полный текст правила
     */
    String text();

    /**
     * Уровень серьезности правила
     */
    Severity severity();
}
