package io.github.marattim.raif_api_guide.integration_tests;

import com.openai.client.okhttp.OpenAIOkHttpClient;
import io.github.marattim.raif_api_guide.llm_impl.prompt.OnlyLinesPrompt;
import io.github.marattim.raif_api_guide.llm_impl.prompt.Prompt;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = System.getenv("LLM_API_URL");
        String apiKey = System.getenv("LLM_API_KEY");

        // Тут можно настроить какие именно модели тестируем
        List<String> models = new OpenAiModelsFromApi(
            OpenAIOkHttpClient.builder()
                .baseUrl(url)
                .apiKey(apiKey)
                .build()
        ).stream()
            .filter("medium"::equals)
            .toList();

        Prompt prompt = new OnlyLinesPrompt();

        for (String model : models) {
            ExperimentParams params = new ExperimentParams(
                model,
                0.0,
                prompt,
                url
            );
            new Experiment(
                params.file(),
                params,
                apiKey
            ).run();
        }
    }
}
