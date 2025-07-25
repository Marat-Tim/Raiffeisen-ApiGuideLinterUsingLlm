package io.github.marattim.raif_api_guide.llm_impl;

import com.openai.errors.RateLimitException;

import java.time.Duration;

/**
 * Декоратор, который в случае RateLimitException делает задержку и повторяет запрос
 */
public class WaitingLlm implements Llm {
    private final Llm llm;
    private final Duration wait;
    private final int retries;

    public WaitingLlm(Llm llm, Duration wait, int retries) {
        this.llm = llm;
        this.wait = wait;
        this.retries = retries;
    }

    @Override
    public Context context(String prompt) {
        var context = llm.context(prompt);
        return message -> {
            for (int i = 0; i < retries; i++) {
                try {
                    return context.response(message);
                } catch (RateLimitException e) {
                    System.out.println("Превышен лимит запросов, ждем " + wait);
                    try {
                        Thread.sleep(wait);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(ex);
                    }
                }
            }
            throw new RuntimeException("Не получилось сделать запрос после " + retries + " попыток");
        };
    }
}
