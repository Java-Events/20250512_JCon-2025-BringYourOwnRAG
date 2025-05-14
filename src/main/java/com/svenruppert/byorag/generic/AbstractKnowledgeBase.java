package com.svenruppert.byorag.generic;

import com.svenruppert.dependencies.core.logger.HasLogger;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;

import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractKnowledgeBase
    implements HasLogger {

  public static final String BASE_URL = "http://localhost:11434"; // local ollama base url
  private static RAGConfig mistral = new RAGConfig("mistral", 8, 4096);
  private static RAGConfig gemma = new RAGConfig("gemma", 2, 3072);
  private static RAGConfig llama = new RAGConfig("llama3.2", 5, 3072);
  private static RAGConfig llamaWithNomic = new RAGConfig("llama3.2", 5, 768);

//    public static RAGConfig ACTIVE_RAG_CONFIG = mistral;
//  public static RAGConfig ACTIVE_RAG_CONFIG = gemma;
  public static final RAGConfig ACTIVE_RAG_CONFIG = llama;
//  public static final RAGConfig ACTIVE_RAG_CONFIG = llamaWithNomic;


  protected abstract EmbeddingModel embeddingModel();
  protected abstract EmbeddingStore<TextSegment> store();

  public List<String> search(String query, int topK) {
    var embeddingSearchRequest = embeddingQuery(query, topK);
    logger().info("Search vector with dimension {}", embeddingSearchRequest.queryEmbedding().vector().length);
    var matches = searchInVectorDB(embeddingSearchRequest);
    return convertToContext(matches);
  }

  @NotNull
  protected List<String> convertToContext(List<EmbeddingMatch<TextSegment>> matches) {
    return matches
        .stream()
        .map(v -> {
          logger().info("Found match inside Store -> {} ", v.embedded().text());
          return v.embedded().text();
        }).toList();
  }

  protected List<EmbeddingMatch<TextSegment>> searchInVectorDB(EmbeddingSearchRequest embeddingSearchRequest) {
    logger().info("Searching inside Store");
    List<EmbeddingMatch<TextSegment>> matches = store()
        .search(embeddingSearchRequest)
        .matches();
    logger().info("Found {} matches inside Store", matches.size());
    return matches;
  }

  @NotNull
  protected EmbeddingSearchRequest embeddingQuery(String query, int topK) {
    logger().info("Embedding the Query {}", query);
    Embedding queryEmbedding = embeddingModel()
        .embed(query)
        .content();

    var length = queryEmbedding.vector().length;
    logger().info("Embedding length {}", length);
    var dimension = queryEmbedding.dimension();
    logger().info("Embedding dimension {}", dimension);

    return EmbeddingSearchRequest
        .builder()
        .queryEmbedding(queryEmbedding)
        .maxResults(topK)
        .build();
  }

  public record RAGConfig(String modelName, int hitCount, int vectorDimensions) { }

}