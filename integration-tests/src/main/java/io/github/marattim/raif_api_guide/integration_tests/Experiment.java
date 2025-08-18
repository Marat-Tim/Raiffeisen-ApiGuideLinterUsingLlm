package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.common.Timed;
import io.github.marattim.raif_api_guide.llm_impl.OpenApiUsingLlm;
import io.github.marattim.raif_api_guide.llm_impl.part.FullSpec;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Эксперимент с одной конкретной реализацией.
 * Все результаты запуска и статистика
 * выводятся в переданный поток вывода
 */
public class Experiment {
    private final PrintStream out;

    public Experiment(PrintStream out) {
        this.out = out;
    }

    public void run() {
        try {
            out.println("# Запуск");
            out.println();
            Path file = Path.of("./integration-tests/src/main/resources/example.yaml").toAbsolutePath();

            Timed.Result<List<Defect>> timed = new Timed<>(
                new LoggingOpenApi(
                    new OpenApiUsingLlm(
                        new FullSpec(
                            Files.readString(file)
                        ),
                        System.getenv("LLM_API_KEY")
                    )
                ).defects()::toList
            ).value();
            List<Defect> defects = timed.result();
            defects.forEach(
                defect -> out.printf(
                    """
                        - id: %s
                        
                          selection: %s,%s - %s,%s
                        
                          description: %s
                        
                        """,
                    defect.id(),
                    defect.selection().start().line(),
                    defect.selection().start().character(),
                    defect.selection().end().line(),
                    defect.selection().end().character(),
                    defect.description()
                )
            );
            out.println();
            out.println("# Результаты");
            out.println();
            out.println("Время выполнения: " + timed.duration());
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
