package io.github.marattim.raif_api_guide.llm_impl.rule;

import io.github.marattim.raif_api_guide.common.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfoTableTest {
    @Test
    void shouldCorrectFindSeverityAndId() {
        String raw = new Resource("/example_rule.md").get();
        InfoTable table = new InfoTable(raw);
        assertEquals("path-kebab-case", table.id());
        assertEquals("MUST", table.severity());
    }

}