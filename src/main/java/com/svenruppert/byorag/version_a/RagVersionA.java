package com.svenruppert.byorag.version_a;

import com.svenruppert.byorag.generic.AbstractRAG;

import java.util.List;

import static com.svenruppert.byorag.generic.AbstractKnowledgeBase.ACTIVE_RAG_CONFIG;
import static com.svenruppert.byorag.version_a.ExternalDataProviderA.externalData;

public class RagVersionA
    extends AbstractRAG {


  public static void main(String[] args) {
    var rag = new RagVersionA();
    rag.askTheQuestion();
  }

  public void askTheQuestion() {
    logger().info("create additional information..");
    var externalData = externalData();

    logger().info("creating the KnowledgeBase");
    KnowledgeBaseA kb = new KnowledgeBaseA(externalData);
    // Beispiel-Frage
    logger().info("start asking the question.");
    String question = "Who can share their tent with someone else?";

    List<String> kontext = kb.search(question, ACTIVE_RAG_CONFIG.hitCount()); // top X relevante Einträge
    logger().info("additional context created");
    var prompt = createSimplePrompt(kontext, question);

    // Anfrage an das LLM
    logger().info("The generated prompt: {}", prompt);

    logger().info("asking the LLM..");
    String antwort = assistant.chat(prompt);
    System.out.println("Antwort:\n" + antwort);
  }


}
