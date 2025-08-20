package io.github.marattim.raif_api_guide.llm_impl;

import dev.langchain4j.model.chat.ChatModel;
import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.OpenApi;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.fake.FakeChatModel;
import io.github.marattim.raif_api_guide.llm_impl.fake.FakeRule;
import io.github.marattim.raif_api_guide.llm_impl.fake.FakeSpecPart;
import io.github.marattim.raif_api_guide.llm_impl.prompt.OnlyLinesPrompt;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenApiUsingLlmTest {
    @Test
    void shouldReturnDefectParsedFromLlmResponse() {
        SpecPart part = new FakeSpecPart();
        Rule rule = new FakeRule();
        ChatModel llm = new FakeChatModel("1,потому что\n3,потому что");
        OpenApi api = new OpenApiUsingLlm(
            Collections.singleton(part),
            Collections.singleton(rule),
            new MessagePipeline(
                llm,
                new OnlyLinesPrompt()
            )
        );
        List<? extends Defect> defects = api.defects().toList();
        assertDefect(defects.get(0), part.line() + 1, rule.id());
        assertDefect(defects.get(1), part.line() + 3, rule.id());
    }

    private static void assertDefect(Defect defect, int line, String id) {
        assertEquals(line, defect.selection().start().line(), "Неправильный номер строки");
        assertEquals(1, defect.selection().start().character(), "Неправильный номер символа");
        assertEquals(id, defect.id(), "Неправильный id правила");
    }
}
