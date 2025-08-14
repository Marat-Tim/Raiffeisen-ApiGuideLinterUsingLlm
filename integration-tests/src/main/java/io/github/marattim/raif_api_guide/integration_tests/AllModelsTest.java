package io.github.marattim.raif_api_guide.integration_tests;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.llm_impl.MessagePipeline;
import io.github.marattim.raif_api_guide.llm_impl.OpenApiUsingLlm;
import io.github.marattim.raif_api_guide.llm_impl.part.SmallSpecPart;
import io.github.marattim.raif_api_guide.llm_impl.prompt.OnlyLinesPrompt;
import io.github.marattim.raif_api_guide.llm_impl.rule.RulesFromGithub;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AllModelsTest {
    /**
     * Собирает все ответы от моделей и сохраняет их в файл statistics.csv
     */
    public static void main(String[] args) {
        String url = System.getenv("LLM_API_URL");
        String apiKey = System.getenv("LLM_API_KEY");

        OpenAIClient client = OpenAIOkHttpClient.builder()
            .baseUrl(url)
            .apiKey(apiKey)
            .build();

        // Тут можно настроить какие именно модели тестируем
        List<String> models = new ModelsToTest(client)
            .stream()
            .filter("medium"::equals)
            .toList();

        for (String model : models) {
            try {
                System.out.println(model);
                Set<String> ids = new ParsedErrorsFromSpec()
                    .stream()
                    .map(ParsedDefectArea::id)
                    .collect(Collectors.toSet());
                Path file = Path.of("./integration-tests/src/main/resources/example.yaml").toAbsolutePath();
                List<Defect> defects = new OpenApiUsingLlm(
                    List.of(
                        new SmallSpecPart(
                            file,
                            Files.readString(file)
                        )
                    ),
                    new RulesFromGithub()
                        .stream()
                        .filter(r -> ids.contains(r.id()))
                        .toList(),
                    new MessagePipeline(
                        new LoggingChatModel(
                            OpenAiChatModel.builder()
                                .baseUrl(url)
                                .apiKey(apiKey)
                                .temperature(0.0)
                                .modelName(model)
                                .maxRetries(10)
                                .timeout(Duration.ofSeconds(10))
                                .build()
                        ),
                        new OnlyLinesPrompt()
                    )
                ).defects().toList();
                System.out.println(
                    new Statistics(
                        new ParsedErrorsFromSpec().stream().toList(),
                        defects
                    ).value()
                );
                defects.forEach(
                    defect -> System.out.printf(
                        "id=%s file://%s:%s%n",
                        defect.id(),
                        defect.path(),
                        defect.selection().start().line()
                    )
                );
            } catch (Exception e) {
                System.out.printf("Для модели=%s возникла ошибка %s: %s%n", model, e.getClass(), e.getMessage());
                e.printStackTrace();
                System.out.println("----------------------------------------");
                System.out.println();
            }
        }
    }
}
