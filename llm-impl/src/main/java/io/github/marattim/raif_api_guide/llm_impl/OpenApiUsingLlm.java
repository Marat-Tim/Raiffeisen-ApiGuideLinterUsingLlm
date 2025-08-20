package io.github.marattim.raif_api_guide.llm_impl;

import dev.langchain4j.model.openai.OpenAiChatModel;
import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.part.FullSpec;
import io.github.marattim.raif_api_guide.llm_impl.prompt.OnlyLinesPrompt;
import io.github.marattim.raif_api_guide.llm_impl.rule.OnlyGoodGithubRules;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Реализация спецификации open api через llm
 */
public class OpenApiUsingLlm implements OpenApi {
    private final Collection<? extends SpecPart> parts;
    private final Collection<Rule> rules;
    private final MessagePipeline pipeline;

    /**
     * Самая лучшая модель на данный момент.
     * Для работы требует переменную LLM_API_URL в окружении
     */
    public OpenApiUsingLlm(FullSpec spec, String apiKey) {
        this(
            spec.parts().toList(),
            new OnlyGoodGithubRules().stream().toList(),
            new MessagePipeline(
                OpenAiChatModel.builder()
                    .baseUrl(safeEnvGet("LLM_API_URL"))
                    .apiKey(apiKey)
                    .modelName("ultra-coder")
                    .timeout(Duration.ofSeconds(10))
                    .maxRetries(10)
                    .build(),
                new OnlyLinesPrompt()
            )
        );
    }

    public OpenApiUsingLlm(Collection<? extends SpecPart> parts, Collection<Rule> rules, MessagePipeline pipeline) {
        this.parts = parts;
        this.rules = rules;
        this.pipeline = pipeline;
    }

    @Override
    public Stream<Defect> defects() {
        return rules.stream()
            .flatMap(
                rule -> parts.stream()
                    .flatMap(
                        part -> pipeline.defects(
                            part,
                            rule
                        ).stream()
                    )
            );
    }

    private static String safeEnvGet(String name) {
        String value = System.getenv(name);
        if (value == null) {
            throw new RuntimeException("Необходимо объявить переменную окружения " + name);
        }
        return value;
    }

}
