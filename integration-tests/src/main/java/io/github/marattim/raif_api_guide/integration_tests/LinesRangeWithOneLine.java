package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Selection;

public class LinesRangeWithOneLine implements LinesRange {
    private final int line;

    public LinesRangeWithOneLine(int line) {
        this.line = line;
    }

    @Override
    public boolean contains(Selection selection) {
        return selection.start().line() == line && selection.end().line() == line;
    }
}
