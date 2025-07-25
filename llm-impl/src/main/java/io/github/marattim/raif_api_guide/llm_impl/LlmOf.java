package io.github.marattim.raif_api_guide.llm_impl;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

/**
 * Класс для упрощенного взаимодействия с llm
 */
public class LlmOf implements Llm {
    private final OpenAIClient client;
    private final String model;
    private final double temperature;

    public LlmOf(String url, String apiKey, String model, double temperature) {
        this(
            OpenAIOkHttpClient.builder()
                .baseUrl(url)
                .apiKey(apiKey)
                .build(),
            model,
            temperature
        );
    }

    public LlmOf(OpenAIClient client, String model, double temperature) {
        this.client = client;
        this.model = model;
        this.temperature = temperature;
    }

    @Override
    public Context context(String prompt) {
        return new ContextOf(prompt);
    }

    private class ContextOf implements Context {
        private final String prompt;

        ContextOf(String prompt) {
            this.prompt = prompt;
        }

        @Override
        public String response(String message) {
            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(model)
                .addSystemMessage(prompt)
                .addUserMessage(message)
                .temperature(temperature)
                .build();
            var response = client.chat().completions().create(params);
            return response.choices()
                .getFirst()
                .message()
                .content()
                .orElseThrow(() -> new IllegalStateException("Получили пустой ответ от llm"));
        }

    }

}
