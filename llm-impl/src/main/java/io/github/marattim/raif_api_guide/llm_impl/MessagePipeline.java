package io.github.marattim.raif_api_guide.llm_impl;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import io.github.marattim.raif_api_guide.Defect;
import io.github.marattim.raif_api_guide.SpecPart;
import io.github.marattim.raif_api_guide.llm_impl.prompt.Prompt;
import io.github.marattim.raif_api_guide.llm_impl.rule.Rule;

import java.util.Collection;

/**
 * Пайплайн, который проходит часть спецификации вместе с определенным правилом,
 * от отправки в модель до парсинга ответа
 */
public class MessagePipeline {
    private final ChatModel llm;
    private final Prompt prompt;

    public MessagePipeline(ChatModel llm, Prompt prompt) {
        this.llm = llm;
        this.prompt = prompt;
    }

    public Collection<? extends Defect> defects(SpecPart part, Rule rule) {
        return prompt.parsed(
            llm.chat(
                SystemMessage.from(prompt.text(rule)),
                UserMessage.from(prompt.transformedMessage(part.text()))
            ).aiMessage().text(),
            part,
            rule
        );
    }
}
