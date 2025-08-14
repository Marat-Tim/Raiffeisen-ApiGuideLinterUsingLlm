package io.github.marattim.raif_api_guide.llm_impl.fake;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.mockito.Mockito;

public class FakeChatModel implements ChatModel {
    private final String response;

    public FakeChatModel(String response) {
        this.response = response;
    }

    @Override
    public ChatResponse chat(ChatRequest chatRequest) {
        ChatResponse rs = Mockito.mock(ChatResponse.class);
        AiMessage message = Mockito.mock(AiMessage.class);
        Mockito.when(message.text()).thenReturn(response);
        Mockito.when(rs.aiMessage()).thenReturn(message);
        return rs;
    }
}
