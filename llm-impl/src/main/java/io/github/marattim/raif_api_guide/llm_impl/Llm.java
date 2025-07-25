package io.github.marattim.raif_api_guide.llm_impl;

/**
 * Удобный интерфейс для общения с LLM
 */
public interface Llm {
    /**
     * Создает контекст с определенным промптом
     */
    Context context(String prompt);

    interface Context {
        /**
         * Ответ на сообщение(может учитывать историю сообщений, но не обязательно)
         */
        String response(String message);
    }
}
