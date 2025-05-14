package com.svenruppert.byorag.version_b;

import com.svenruppert.dependencies.core.logger.HasLogger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QdrantCollectionInitializer
    implements HasLogger {

  private final String host;
  private final int port;

  public QdrantCollectionInitializer(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void recreateCollection(String collectionName, int vectorSize) {
    try (HttpClient client = HttpClient.newHttpClient()) {
      URI uri = URI.create("http://" + host + ":" + port + "/collections/" + collectionName);
      logger().info("Recreating collection {}", collectionName);

      // 1. Versuche alte Collection zu löschen
      HttpRequest deleteRequest = HttpRequest.newBuilder()
          .uri(uri)
          .DELETE()
          .build();

      HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
      if (deleteResponse.statusCode() == 200) {
        logger().info("Deleted existing collection: {}", collectionName);
      } else {
        logger().info("No existing collection to delete or deletion failed: {}", deleteResponse.body());
      }

      // 2. Neue Collection anlegen
      String body = """
          {
            "vectors": {
              "size": %d,
              "distance": "Cosine"
            }
          }
          """.formatted(vectorSize);

      HttpRequest createRequest = HttpRequest.newBuilder()
          .uri(uri)
          .header("Content-Type", "application/json")
          .PUT(HttpRequest.BodyPublishers.ofString(body))
          .build();

      HttpResponse<String> createResponse = client.send(createRequest, HttpResponse.BodyHandlers.ofString());
      if (createResponse.statusCode() == 200) {
        logger().info("Collection created: {}", collectionName);
      } else {
        logger().warn("Failed to create collection: {}", createResponse.body());
      }

    } catch (IOException | InterruptedException e) {
      logger().error("Exception during recreateCollection: {}", e.getMessage());
    }
  }
}
