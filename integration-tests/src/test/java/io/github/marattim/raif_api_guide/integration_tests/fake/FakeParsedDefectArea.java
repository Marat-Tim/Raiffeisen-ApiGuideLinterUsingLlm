package io.github.marattim.raif_api_guide.integration_tests.fake;

import io.github.marattim.raif_api_guide.integration_tests.LinesRange;
import io.github.marattim.raif_api_guide.integration_tests.ParsedDefectArea;

public class FakeParsedDefectArea implements ParsedDefectArea {
    private final boolean contains;
    private final String id;

    public FakeParsedDefectArea(boolean contains, String id) {
        this.contains = contains;
        this.id = id;
    }

    @Override
    public LinesRange lines() {
        return selection -> contains;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String number() {
        return "";
    }
}
