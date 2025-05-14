package com.svenruppert.vectordb;

import java.util.stream.IntStream;

public class Vectorizer {
  private Vectorizer() {
  }

  public static float[] vectorize(String input, int dimensions) {
    float[] vector = new float[dimensions];
    int hash = input.toLowerCase().hashCode();

    for (int i = 0; i < dimensions; i++) {
      int component = Integer.rotateLeft(hash, i * 7);
      vector[i] = (float) ((component % 1000) / 1000.0); // Wert zwischen -1 und 1
    }

    normalize(vector);
    return vector;
  }

  private static void normalize(float[] vector) {
    float sum = 0f;
    for (float v : vector) sum += v * v;
    float norm = (float) Math.sqrt(sum);
    if (norm > 0) {
      IntStream
          .range(0, vector.length)
          .forEach(i -> vector[i] /= norm);
    }
  }
}
