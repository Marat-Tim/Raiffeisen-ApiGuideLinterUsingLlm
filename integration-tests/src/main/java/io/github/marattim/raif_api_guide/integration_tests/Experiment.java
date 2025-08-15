package io.github.marattim.raif_api_guide.integration_tests;

import dev.langchain4j.model.openai.OpenAiChatModel;
import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.llm_impl.MessagePipeline;
import io.github.marattim.raif_api_guide.llm_impl.OpenApiUsingLlm;
import io.github.marattim.raif_api_guide.llm_impl.part.SmallSpecPart;
import io.github.marattim.raif_api_guide.llm_impl.prompt.OnlyLinesPrompt;
import io.github.marattim.raif_api_guide.llm_impl.rule.RulesFromGithub;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Experiment {
    private final PrintStream out;
    private final ExperimentParams params;
    private final String apiKey;

    public Experiment(PrintStream out, ExperimentParams params, String apiKey) {
        this.out = out;
        this.params = params;
        this.apiKey = apiKey;
    }

    public void run() {
        try {
            out.println("# Параметры");
            out.println();
            out.println(params);
            out.println();
            out.println("# Запуск");
            out.println();
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
                            .baseUrl(params.url())
                            .apiKey(apiKey)
                            .temperature(0.0)
                            .modelName(params.model())
                            .maxRetries(10)
                            .timeout(Duration.ofSeconds(10))
                            .build()
                    ),
                    new OnlyLinesPrompt()
                )
            ).defects().toList();
            defects.forEach(
                defect -> out.printf(
                    "id=%s %s:%s%n%n",
                    defect.id(),
                    Path.of("").toAbsolutePath().relativize(defect.path()),
                    defect.selection().start().line()
                )
            );
            out.println();
            out.println("# Результаты");
            out.println();
            out.println(
                new Statistics(
                    new ParsedErrorsFromSpec().stream().toList(),
                    defects
                ).value()
            );
        } catch (Exception e) {
            out.printf("Возникла ошибка %s: %s%n", e.getClass(), e.getMessage());
            e.printStackTrace(out);
            out.println();
        } finally {
            out.flush();
        }
    }
}
