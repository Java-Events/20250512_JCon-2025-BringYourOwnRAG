package com.svenruppert.byorag.version_c;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svenruppert.byorag.generic.AbstractRAG;
import com.svenruppert.byorag.version_b.KnowledgeBaseB;

import java.util.List;
import java.util.Map;

import static com.svenruppert.byorag.generic.AbstractKnowledgeBase.ACTIVE_RAG_CONFIG;
import static com.svenruppert.byorag.version_b.ExternalDataProviderB.externalData;
import static com.svenruppert.byorag.version_c.QuestionsForRagC.*;

public class RagVersionC
    extends AbstractRAG {


  public static void main(String[] args) {
    var rag = new RagVersionC();
    rag.askTheQuestion();
  }

  @Override
  public void askTheQuestion() {
    logger().info("create additional information..");
    var externalData = externalData();

    logger().info("creating the KnowledgeBase");
    KnowledgeBaseB kb = new KnowledgeBaseB(externalData);
    // Beispiel-Frage
    logger().info("start asking the question.");
    String frage = QUESTION_VERSION_E;

    //TODO How to deal with too many items?
    List<String> kontext = kb.search(frage, ACTIVE_RAG_CONFIG.hitCount() + 10);

    logger().info("additional context created");
    var prompt = createSimplePrompt(kontext, frage);

    // Anfrage an das LLM
    logger().info("The generated prompt: {}", prompt);

    logger().info("asking the LLM..");
    String antwort = assistant.chat(prompt);
    logger().info("Response:\n\n{}", antwort);

    mapToJavaObjects(antwort);


  }

  private void mapToJavaObjects(String antwort) {
    logger().info("Init Jackson");
    ObjectMapper mapper = new ObjectMapper();

    record PersonGear(String name, List<String> items) { }
    Map<String, List<String>> raw = null;
    try {
      raw = mapper.readValue(antwort, new TypeReference<>() { });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    List<PersonGear> gearList = raw
        .entrySet()
        .stream()
        .map(entry -> new PersonGear(entry.getKey(), entry.getValue()))
        .toList();

    gearList.forEach(System.out::println);
  }
}