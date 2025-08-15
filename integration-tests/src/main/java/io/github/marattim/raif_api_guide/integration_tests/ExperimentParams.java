package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.llm_impl.prompt.Prompt;
import org.jspecify.annotations.NonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ExperimentParams(
    String model,
    double temperature,
    Prompt prompt,
    String url
) {
    @NonNull
    @Override
    public String toString() {
        return """
            Модель: %s
            
            Температура: %s
            
            Промпт: %s
            """.formatted(model, temperature, prompt.getClass());
    }

    public PrintStream file() {
        String path = "integration-tests/results/%s_%s.md"
            .formatted(
                prompt.getClass().getSimpleName(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss"))
            );
        Path dir = Path.of(path).getParent();
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return new PrintStream(new FileOutputStream(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
