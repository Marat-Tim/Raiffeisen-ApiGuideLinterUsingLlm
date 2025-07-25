package io.github.marattim.raif_api_guide.llm_impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Resource {
    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public String get() {
        try (InputStream input = getClass().getResourceAsStream(path)) {
            if (input == null) {
                throw new IllegalStateException("input равен null");
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
