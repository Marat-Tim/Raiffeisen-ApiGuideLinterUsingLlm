package io.github.marattim.raif_api_guide.integration_tests;

import io.github.marattim.raif_api_guide.Selection;

public class LinesRangeBetweenTwoLines implements LinesRange {
    private final int start;
    private final int end;

    public LinesRangeBetweenTwoLines(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean contains(Selection selection) {
        return selection.start().line() >= start && selection.end().line() <= end;
    }
}
