package com.svenruppert.byorag.hellorag;

import com.svenruppert.byorag.generic.AbstractKnowledgeBase;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.util.Map;

public class KnowledgeBase
    extends AbstractKnowledgeBase {



  public KnowledgeBase(Map<String, String> externalData) {
    for (var entry : externalData.entrySet()) {
      TextSegment segment = TextSegment.from(entry.getValue());
      logger().info("TextSegment : {}", segment.text());
      Embedding embedding = embeddingModel().embed(segment.text()).content();
      store().add(embedding, segment);
    }
  }


  @Override
  protected EmbeddingModel embeddingModel() {
    return OllamaEmbeddingModel.builder()
        .baseUrl(BASE_URL)
        .modelName(ACTIVE_RAG_CONFIG.modelName())
        .build();
  }

  @Override
  protected EmbeddingStore<TextSegment> store() {
    return new InMemoryEmbeddingStore<>();
  }
}