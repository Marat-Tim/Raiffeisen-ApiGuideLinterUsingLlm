package io.github.marattim.raif_api_guide.integration_tests;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Файл в который нужно записать результаты эксперимента
 */
public class ResultPrintStream {
    public PrintStream value() {
        String path = "integration-tests/results/%s.md"
            .formatted(
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
