package com.svenruppert.byorag.hellorag;

import com.svenruppert.byorag.generic.AbstractRAG;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.svenruppert.byorag.generic.AbstractKnowledgeBase.ACTIVE_RAG_CONFIG;
import static com.svenruppert.byorag.hellorag.ExternalDataProvider.externalData;

public class HelloRAG
    extends AbstractRAG {

  protected static final String NO_HALLUCINATIONS
      = "Answer based only on the context. No assumptions, no hallucinations.";


  private final String questionA = "Who can share their tent with someone else?";
  private final String questionB = "Who can share their tent with someone else? " + NO_HALLUCINATIONS;


  public static void main(String[] args) {
    var rag = new HelloRAG();
    rag.askTheQuestion();
  }

  public void askTheQuestion() {
    logger().info("create additional information..");
    var externalData = externalData();
    // KnowledgeBase mit EmbeddingStore
    logger().info("creating the KnowledgeBase");
    KnowledgeBase kb = new KnowledgeBase(externalData);
    // Beispiel-Frage
    logger().info("start asking the question.");
    List<String> kontext = kb.search(questionA, ACTIVE_RAG_CONFIG.hitCount()); // top 2 relevante Einträge

    logger().info("additional context created");
//    var prompt = createPrompt(kontext, questionA);
//    var prompt = createPromptSimple(kontext, questionA);
    var prompt = createPromptVersionA(kontext, questionA);

    // Anfrage an das LLM
    logger().info("The generated prompt: {}", prompt);

    logger().info("asking the LLM..");
    String antwort = assistant.chat(prompt);
    System.out.println("Antwort:\n" + antwort);
  }


  @NotNull
  private String createPromptVersionA(List<String> kontext, String question) {
    // Finaler Prompt
    return """
         Answer the following question **based solely** on the provided equipment information.
         Use only facts from the context. Do not make assumptions.
         Justify your decisions.
        \s
         Equipment:
         %s
        \s
         Question:
         %s
       \s""".formatted(String.join("\n", kontext), question);

  }
}
