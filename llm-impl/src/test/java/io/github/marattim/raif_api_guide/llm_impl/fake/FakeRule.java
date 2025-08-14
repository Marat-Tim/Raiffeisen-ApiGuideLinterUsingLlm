package io.github.marattim.raif_api_guide.llm_impl.fake;

import io.github.marattim.raif_api_guide.Severity;
import io.github.marattim.raif_api_guide.llm_impl.Resource;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;

public class FakeRule implements Rule {
    @Override
    public String id() {
        return "path-kebab-case";
    }

    @Override
    public String text() {
        return new Resource("/example_rule.md").get();
    }

    @Override
    public Severity severity() {
        return Severity.MUST;
    }
}
