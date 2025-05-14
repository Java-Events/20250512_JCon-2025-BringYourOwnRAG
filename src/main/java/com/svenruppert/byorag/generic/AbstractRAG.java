package com.svenruppert.byorag.generic;

import com.svenruppert.dependencies.core.logger.HasLogger;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.svenruppert.byorag.generic.AbstractKnowledgeBase.ACTIVE_RAG_CONFIG;
import static com.svenruppert.byorag.generic.AbstractKnowledgeBase.BASE_URL;
import static dev.langchain4j.model.chat.request.ResponseFormat.TEXT;

public abstract class AbstractRAG
    implements HasLogger {

  protected final ChatModel chatModel = OllamaChatModel
      .builder()
      .baseUrl(BASE_URL)
      .modelName(ACTIVE_RAG_CONFIG.modelName())
      .responseFormat(TEXT)
      .build();

  protected RagAssistant assistant = AiServices
      .builder(RagAssistant.class)
      .chatModel(chatModel)
      .build();

  public abstract void askTheQuestion();

  @NotNull
  protected String createSimplePrompt(List<String> kontext, String question) {
    // Finaler Prompt
    return """
        Context:
        %s

        Question:
        %s
        """.formatted(String.join("\n", kontext), question);
  }
}