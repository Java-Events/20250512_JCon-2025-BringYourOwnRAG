package com.svenruppert.vectordb.version01;

import com.svenruppert.vectordb.VectorEntry;
import com.svenruppert.vectordb.Vectorizer;

import java.util.Map;

public class HelloVectorV01 {
  private HelloVectorV01() {
  }

  public static void main(String[] args) {
    VectorStoreV01 store = new VectorStoreV01();

    // Namen einfügen
    addName(store, "1", "Anna");
    addName(store, "2", "Anne");
    addName(store, "3", "Anja");
    addName(store, "4", "Andreas");
    addName(store, "5", "Bernd");
    addName(store, "6", "Bruno");

    // Ähnlichkeitssuche für "Anni"
    String query = "Anni";
    float[] queryVec = Vectorizer.vectorize(query, 10);

    var results = store.searchWithScores(queryVec, 5);

    System.out.printf("Ähnliche Namen zu \"%s\":\n", query);
    for (Map.Entry<VectorEntry, Float> result : results) {
      System.out.printf("→ %-8s [Score: %.4f]\n", result.getKey().name(), result.getValue());
    }
  }

  private static void addName(VectorStoreV01 store, String id, String name) {
    float[] vec = Vectorizer.vectorize(name, 10);
    store.add(new VectorEntry(id, vec, name));
  }
}