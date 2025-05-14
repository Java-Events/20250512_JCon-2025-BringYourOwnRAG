package com.svenruppert.vectordb;

public class SemanticVectorizer {
  private SemanticVectorizer() {
  }

  public static float[] vectorize(String input, int dimensions) {
    float[] vector = new float[dimensions];
    String clean = input
        .toLowerCase()
        .replaceAll("[^a-z]", ""); // Nur Buchstaben

    if (clean.isEmpty()) return vector;

    // Beispiel: Mapping Buchstaben auf Dimensionen
    for (int i = 0; i < clean.length(); i++) {
      char c = clean.charAt(i);
      int charPos = c - 'a'; // a = 0, z = 25
      int dimIndex = charPos % dimensions;

      // Gewichtung: spätere Buchstaben sind weniger wichtig
      float weight = 1.0f - (i / (float) clean.length());

      vector[dimIndex] += weight;
    }

    normalize(vector);
    return vector;
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

