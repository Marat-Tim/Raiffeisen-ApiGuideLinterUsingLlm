package io.github.marattim.raif_api_guide.integration_tests;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;

import java.time.Duration;

public class LoggingChatModel implements ChatModel {
    private final ChatModel chatModel;

    public LoggingChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public ChatResponse chat(ChatRequest chatRequest) {
        System.out.println("Делаем запрос");
        long start = System.nanoTime();
        ChatResponse response = chatModel.chat(chatRequest);
        long end = System.nanoTime();
        System.out.println("Запрос выполнен за " + Duration.ofNanos(end - start));
        return response;
    }
}
