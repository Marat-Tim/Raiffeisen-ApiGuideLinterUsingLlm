package io.github.marattim.raif_api_guide.llm_impl;

import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.fake.FakeLlm;
import io.github.marattim.raif_api_guide.llm_impl.fake.FakeRule;
import io.github.marattim.raif_api_guide.llm_impl.fake.FakeSpecPart;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenApiUsingLlmTest {
    @Test
    void shouldReturnDefectParsedFromLlmResponse() {
        SpecPart part = new FakeSpecPart();
        Rule rule = new FakeRule();
        Llm llm = new FakeLlm("1,3");
        OpenApi api = new OpenApiUsingLlm(
            Collections.singleton(part),
            Collections.singleton(rule),
            llm
        );
        List<? extends Defect> defects = api.defects().toList();
        assertDefect(defects.get(0), part.line() + 1, rule.id());
        assertDefect(defects.get(1), part.line() + 3, rule.id());
    }

    private static void assertDefect(Defect defect, int line, String id) {
        assertEquals(line, defect.selection().start().line());
        assertEquals(0, defect.selection().start().character());
        assertEquals(id, defect.id());
    }
}
