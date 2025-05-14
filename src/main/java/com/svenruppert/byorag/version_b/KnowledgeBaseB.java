package com.svenruppert.byorag.version_b;

import com.svenruppert.byorag.generic.AbstractKnowledgeBase;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KnowledgeBaseB
    extends AbstractKnowledgeBase {

  protected static final String META_PERSON = "Person";
  protected static final String META_NAME = "Name";
  protected static final String COLLECTION_NAME = "myCollection";
  protected static final String HOST = "localhost";
  protected static final int PORT_REST = 6333;
  protected static final int PORT_GRPC = 6334;


  protected static final QdrantCollectionInitializer INITIALIZER = new QdrantCollectionInitializer(HOST, PORT_REST);
  private final EmbeddingStore<TextSegment> store = store();
  private final EmbeddingModel embeddingModel = embeddingModel();

  public KnowledgeBaseB(List<ExternalDataProviderB.Person> externalData) {

    INITIALIZER.recreateCollection(COLLECTION_NAME, ACTIVE_RAG_CONFIG.vectorDimensions());

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

            Embedding embedding = embeddingModel
                .embed(segment.text())
                .content();

            var add = store.add(embedding, segment);
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
    return QdrantEmbeddingStore
        .builder()
        .host(HOST)
        .useTls(false)
        .port(PORT_GRPC)
        .collectionName(COLLECTION_NAME)
//      .vectorDimension(384) // oder was dein Modell liefert
//      .distance(Distance.COSINE)
        .build();
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