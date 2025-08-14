package io.github.marattim.raif_api_guide.llm_impl.rule;

import io.github.marattim.raif_api_guide.llm_impl.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RulesFromGithubTest {
    @Test
    void shouldParse23Rules() {
        assertEquals(
            23,
            new RulesFromGithub(
                new Resource("/all_rules.md").get()
            ).stream().toList().size()
        );
    }

}