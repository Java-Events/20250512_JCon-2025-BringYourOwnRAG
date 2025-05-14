package com.svenruppert.vectordb;

import java.util.HashSet;
import java.util.Set;

public class TrigramVectorizer {

  private TrigramVectorizer() {
  }

  public static float[] vectorize(String input, int dimensions) {
    float[] vector = new float[dimensions];
    Set<String> trigrams = extractTrigrams(input.toLowerCase());

    for (String trigram : trigrams) {
      int hash = trigram.hashCode();
      int index = Math.abs(hash % dimensions);
      vector[index] += 1.0f;
    }

    normalize(vector);
    return vector;
  }

  private static Set<String> extractTrigrams(String input) {
    Set<String> trigrams = new HashSet<>();
    String padded = "#" + input + "#";
    for (int i = 0; i < padded.length() - 2; i++) {
      trigrams.add(padded.substring(i, i + 3));
    }
    return trigrams;
  }

  private static void normalize(float[] vector) {
    float sum = 0f;
    for (float v : vector) sum += v * v;
    float norm = (float) Math.sqrt(sum);
    if (norm > 0) {
      for (int i = 0; i < vector.length; i++) {
        vector[i] /= norm;
      }
    }
  }
}
