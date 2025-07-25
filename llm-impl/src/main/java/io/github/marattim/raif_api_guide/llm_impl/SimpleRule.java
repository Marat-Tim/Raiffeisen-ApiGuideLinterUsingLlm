package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.Severity;

record SimpleRule(String id, String text, Severity severity) implements Rule {
    SimpleRule(String raw) {
        this(
            new InfoTable(raw),
            raw
        );
    }

    private SimpleRule(InfoTable table, String raw) {
        this(
            table.id(),
            raw,
            Severity.valueOf(table.severity())
        );
    }

}
