package io.github.marattim.raif_api_guide.integration_tests;

import com.openai.client.OpenAIClient;
import com.openai.models.models.Model;

import java.util.stream.Stream;

public class ModelsToTest {
    private final OpenAIClient client;

    public ModelsToTest(OpenAIClient client) {
        this.client = client;
    }

    public Stream<String> stream() {
        return client.models()
            .list()
            .data()
            .stream()
            .map(Model::id);
    }
}
