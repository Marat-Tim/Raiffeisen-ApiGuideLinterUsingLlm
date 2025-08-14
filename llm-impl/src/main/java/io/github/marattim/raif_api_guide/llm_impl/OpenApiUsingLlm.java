package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Реализация спецификации open api через llm
 */
public class OpenApiUsingLlm implements OpenApi {
    private final Collection<? extends SpecPart> parts;
    private final Collection<Rule> rules;
    private final MessagePipeline pipeline;

    public OpenApiUsingLlm(Collection<? extends SpecPart> parts, Collection<Rule> rules, MessagePipeline pipeline) {
        this.parts = parts;
        this.rules = rules;
        this.pipeline = pipeline;
    }

    // TODO сделать устойчивым к неправильному формату ответов от модели
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

}
