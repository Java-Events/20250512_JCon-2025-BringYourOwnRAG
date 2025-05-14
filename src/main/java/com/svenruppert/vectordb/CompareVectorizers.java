package com.svenruppert.vectordb;

public class CompareVectorizers {

  private CompareVectorizers() {
  }

  public static void main(String[] args) {
    System.out.println("SimpleVectorizer ");
    simpleVectorizer();
    System.out.println("SemanticVectorizer ");
    semanticVectorizer();
    System.out.println("TrigramVectorizer ");
    trigramVectorizer();

  }

  private static void trigramVectorizer() {
    float[] a = TrigramVectorizer.vectorize("Anna", 10);
    float[] b = TrigramVectorizer.vectorize("Anne", 10);
    float[] c = TrigramVectorizer.vectorize("Bernd", 10);

    System.out.println("Anna vs Anne:  " + cosine(a, b));
    System.out.println("Anna vs Bernd: " + cosine(a, c));
  }

  private static void semanticVectorizer() {
    float[] a = SemanticVectorizer.vectorize("Anna", 10);
    float[] b = SemanticVectorizer.vectorize("Anne", 10);
    float[] c = SemanticVectorizer.vectorize("Bernd", 10);

    System.out.println("Anna vs Anne:  " + cosine(a, b));
    System.out.println("Anna vs Bernd: " + cosine(a, c));
  }

  private static void simpleVectorizer() {
    float[] a = Vectorizer.vectorize("Anna", 10);
    float[] b = Vectorizer.vectorize("Anne", 10);
    float[] c = Vectorizer.vectorize("Bernd", 10);

    System.out.println("Anna vs Anne:  " + cosine(a, b));
    System.out.println("Anna vs Bernd: " + cosine(a, c));
  }


  private static float cosine(float[] v1, float[] v2) {
    float dot = 0, norm1 = 0, norm2 = 0;
    for (int i = 0; i < v1.length; i++) {
      dot += v1[i] * v2[i];
      norm1 += v1[i] * v1[i];
      norm2 += v2[i] * v2[i];
    }
    return dot / ((float) Math.sqrt(norm1 * norm2) + 1e-10f);
  }
}
