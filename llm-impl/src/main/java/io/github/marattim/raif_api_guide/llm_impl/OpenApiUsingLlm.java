package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.SpecPart;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Реализация спецификации open api через llm
 */
public class OpenApiUsingLlm implements OpenApi {
    private final Collection<? extends SpecPart> parts;
    private final Collection<Rule> rules;
    private final Llm llm;

    public OpenApiUsingLlm(Collection<? extends SpecPart> parts, Collection<Rule> rules, Llm llm) {
        this.parts = parts;
        this.rules = rules;
        this.llm = llm;
    }

    // TODO сделать устойчивым к неправильному формату ответов от модели
    @Override
    public Stream<Defect> defects() {
        Prompt prompt = new OnlyLinesPrompt();
        return rules.stream()
            .flatMap(rule -> {
                Llm.Context context = llm.context(prompt.text(rule));
                return parts.stream()
                    .flatMap(part -> {
                        String response = context.response(new TextWithNumbers(part.text()).value());
                        return prompt.parsed(response, part, rule).stream();
                    });
            });
    }

}
