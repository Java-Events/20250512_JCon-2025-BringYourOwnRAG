package com.svenruppert.byorag.version_c;

public class QuestionsForRagC {

  public static final String QUESTION_VERSION_A = "List all equipment items together with the name of the owner.";
  public static final String QUESTION_VERSION_B = "List all items showing the combination of the owner's name and the equipment item.";
  public static final String QUESTION_VERSION_C = "List all items showing the combination of the owner's name and the equipment item. " +
                                                  "Return only the data itself, without any additional text.";
  public static final String QUESTION_VERSION_D = "List all items showing the combination of the owner's name and the equipment item. " +
                                                  "Return only the data itself, without any additional text. " +
                                                  "Provide the response strictly as a plain JSON string, without Markdown, comments, or any additional formatting.";
  public static final String QUESTION_VERSION_E = "Who can sleep outside? " +
                                                  "List all items showing the combination of the owner's name and the equipment item. " +
                                                  "Return only the data itself, without any additional text. " +
                                                  "Provide the response strictly as a plain JSON string, without Markdown, comments, or any additional formatting.";
  private QuestionsForRagC() {
  }


}