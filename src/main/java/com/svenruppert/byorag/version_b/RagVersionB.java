package com.svenruppert.byorag.version_b;

import com.svenruppert.byorag.generic.AbstractRAG;

import java.util.List;

import static com.svenruppert.byorag.generic.AbstractKnowledgeBase.ACTIVE_RAG_CONFIG;
import static com.svenruppert.byorag.version_b.ExternalDataProviderB.externalData;

public class RagVersionB
    extends AbstractRAG {

  public static void main(String[] args) {
    var rag = new RagVersionB();
    rag.askTheQuestion();
  }

  public void askTheQuestion() {
    logger().info("Creating additional information...");
    var externalData = externalData();

    logger().info("Creating the KnowledgeBase...");
    KnowledgeBaseB kb = new KnowledgeBaseB(externalData);

    // Example question
    logger().info("Start asking the question...");
    String question = "Who can share their tent with someone else?";
    List<String> context = kb.search(question, ACTIVE_RAG_CONFIG.hitCount()); // Top X relevant entries
    logger().info("Additional context created.");

    var prompt = createSimplePrompt(context, question);

    // Request to the LLM
    logger().info("The generated prompt: {}", prompt);

    logger().info("Asking the LLM...");
    String answer = assistant.chat(prompt);
    System.out.println("Answer:\n" + answer);
  }
}

