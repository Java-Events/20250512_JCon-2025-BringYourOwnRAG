package com.svenruppert.helloworld;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import static dev.langchain4j.model.chat.request.ResponseFormat.TEXT;

public class HelloWorld {
  //  public static final String MODEL_NAME = "llama4"; // try other local ollama model names
  //public static final String MODEL_NAME = "llama3.2"; // try other local ollama model names
//  public  static final String MODEL_NAME = "mistral"; // try other local ollama model names
 //public static final String MODEL_NAME = "gemma3"; // try other local ollama model names
  public static final String MODEL_NAME = "deepseek-r1"; // try other local ollama model names
//  public static final String MODEL_NAME = "qwen3"; // try other local ollama model names

  public static final String BASE_URL = "http://localhost:11434"; // local ollama base url

  private HelloWorld() {
  }

  public static void main(String[] args) {
    ChatModel modelResponseTXT = OllamaChatModel
        .builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .responseFormat(TEXT)
        .build();
    System.out.println(modelResponseTXT.chat("List of the top 10 cites in Germany"));
    //System.out.println(modelResponseTXT.chat("Liste der top 10 Städte in Deutschland"));
  }
}