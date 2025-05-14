package com.svenruppert.vectordb.version02;

import com.svenruppert.vectordb.VectorEntry;
import com.svenruppert.vectordb.Vectorizer;

public class HelloVectorV02 {
  private HelloVectorV02() {
  }

  public static void main(String[] args) {
    VectorStoreV02 store = new VectorStoreV02();

    // Testdaten
    addName(store, "1", "Anna");
    addName(store, "2", "Anne");
    addName(store, "3", "Anja");
    addName(store, "4", "Andreas");
    addName(store, "5", "Bernd");
    addName(store, "6", "Bruno");

    // Suche nach "Anni"
    String query = "Anni";
    float[] queryVec = Vectorizer.vectorize(query, 10);

    var results = store.searchCombined(query, queryVec, 5);

    System.out.printf("Ähnliche Namen zu \"%s\":\n", query);
    for (var result : results) {
      System.out.printf("→ %-8s [Combined Score: %.4f]\n", result.getKey().name(), result.getValue());
    }
  }

  private static void addName(VectorStoreV02 store, String id, String name) {
    float[] vec = Vectorizer.vectorize(name, 10);
    store.add(new VectorEntry(id, vec, name));
  }
}
