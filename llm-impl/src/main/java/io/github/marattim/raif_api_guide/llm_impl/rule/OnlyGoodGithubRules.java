package io.github.marattim.raif_api_guide.llm_impl.rule;

import io.github.marattim.raif_api_guide.common.Streamable;

import java.util.Set;
import java.util.stream.Stream;

/**
 * Правила из гитхаб репозитория, которые Llm может нормально понять
 */
public class OnlyGoodGithubRules implements Streamable<Rule> {
    private static final Set<String> BAD = Set.of(
        "versioning-backward-compatibility",
        "use-most-common-http-codes",
        "use-correct-http-methods",
        "valid-schema-example"
    );

    private final Streamable<Rule> value;

    public OnlyGoodGithubRules() {
        this(new RulesFromGithub());
    }

    public OnlyGoodGithubRules(Streamable<Rule> value) {
        this.value = value;
    }

    public Stream<Rule> stream() {
        return value.stream().filter(
            rule -> !BAD.contains(rule.id())
        );
    }
}
