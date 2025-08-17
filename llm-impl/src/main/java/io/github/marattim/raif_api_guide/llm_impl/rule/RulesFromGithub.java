package io.github.marattim.raif_api_guide.llm_impl.rule;

import io.github.marattim.raif_api_guide.common.Streamable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Список правил, скачанный с
 * <a href="https://github.com/Raiffeisen-DGTL/rest-api-guide/">гитхаб репозитория</a>
 */
public class RulesFromGithub implements Streamable<Rule> {
    private final String content;

    public RulesFromGithub() {
        this(
            URI.create(
                "https://raw.githubusercontent.com/Raiffeisen-DGTL/rest-api-guide/refs/heads/main/rules/rules.md"
            )
        );
    }

    public RulesFromGithub(URI uri) {
        this(download(uri));
    }

    public RulesFromGithub(String content) {
        this.content = content;
    }

    @Override
    public Stream<Rule> stream() {
        return Arrays.stream(content.split("[^#]### "))
            .skip(1)
            .map(SimpleRule::new);
    }

    private static String download(URI uri) {
        try (InputStream input = uri.toURL().openStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
