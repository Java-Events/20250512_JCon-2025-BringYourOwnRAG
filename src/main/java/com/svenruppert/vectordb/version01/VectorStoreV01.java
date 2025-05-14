package com.svenruppert.vectordb.version01;

import com.svenruppert.vectordb.VectorEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VectorStoreV01 {


  private final Map<String, VectorEntry> entries = new HashMap<>();

  public void add(VectorEntry entry) {
    entries.put(entry.id(), entry);
  }

  // Rückgabe: Pair aus VectorEntry + Score
  public List<Map.Entry<VectorEntry, Float>> searchWithScores(float[] queryVector, int k) {
    return entries.values().stream()
        .map(entry -> Map.entry(entry, cosineSimilarity(entry.vector(), queryVector)))
        .sorted((a, b) -> Float.compare(b.getValue(), a.getValue()))
        .limit(k)
        .collect(Collectors.toList());
  }

  private float cosineSimilarity(float[] v1, float[] v2) {
    float dot = 0;
    float norm1 = 0;
    float norm2 = 0;
    for (int i = 0; i < v1.length; i++) {
      dot += v1[i] * v2[i];
      norm1 += v1[i] * v1[i];
      norm2 += v2[i] * v2[i];
    }
    return dot / (float) (Math.sqrt(norm1) * Math.sqrt(norm2) + 1e-10f);
  }
}