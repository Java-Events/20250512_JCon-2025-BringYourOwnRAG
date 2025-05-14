package com.svenruppert.byorag.version_a;

import com.svenruppert.byorag.generic.AbstractKnowledgeBase;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KnowledgeBaseA
    extends AbstractKnowledgeBase {

  protected static final String META_PERSON = "Person";
  protected static final String META_NAME = "Name";

  public KnowledgeBaseA(List<ExternalDataProviderA.Person> externalData) {
    for (var person : externalData) {
      logger().info("Adding person {}", person.name());
      var name = person.name();
      var metadata = new Metadata();
      metadata.put(META_PERSON, name);
      metadata.put(META_NAME, name);
      person
          .info()
          .forEach(infoElement -> {
            TextSegment segment = TextSegment.from(infoElement, metadata);
            logger().info("TextSegment : {}", segment.text());
            Embedding embedding = embeddingModel()
                .embed(segment.text())
                .content();
            var add = store().add(embedding, segment);
            logger().info("Added embedding with ID {}", add);
          });
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

  public List<String> search(String query, int topK) {
    var embeddingSearchRequest = embeddingQuery(query, topK);
    logger().info("Search vector with dimension {}", embeddingSearchRequest.queryEmbedding().vector().length);
    var matches = searchInVectorDB(embeddingSearchRequest);
    return convertToContext(matches);


  }

  @NotNull
  @Override
  protected List<String> convertToContext(List<EmbeddingMatch<TextSegment>> matches) {
    return matches
        .stream()
        .map(v -> {
          var textSegment = v.embedded();
          logger().info("Found match inside Store -> {} ", textSegment.text());
          var metadata = textSegment.metadata();
          return metadata.getString(META_NAME) + ": " + textSegment.text();
        }).toList();
  }
}