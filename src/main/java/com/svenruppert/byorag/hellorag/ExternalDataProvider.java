package com.svenruppert.byorag.hellorag;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalDataProvider {

  private ExternalDataProvider() {
  }

  @NotNull
  public static Map<String, String> externalData() {
    return dataSetA();
  }

  @NotNull
  private static Map<String, String> dataSetA() {
    // Knowledge base: equipment of the participants
    return Map.of(
        "hugo", "Hugo has a two-person tent (MSR Hubba Hubba),\n" +
                "a gas stove,\n" +
                "a first aid kit, and\n" +
                "a headlamp with him.\n",
        "anna", "Anna owns a sleeping bag rated for -5 °C comfort,\n" +
                "a sleeping pad (Therm-a-Rest NeoAir),\n" +
                "trekking poles, and a bivy sack.\n",
        "ben", "Ben is bringing a hammock,\n" +
               "a 3x3m tarp,\n" +
               "a spirit burner, and\n" +
               "a water filter (Katadyn BeFree).\n"
    );
  }


  @NotNull
  private static Map<String, String> dataSetB() {
    // Knowledge base: equipment of the participants
    return Map.of(
        "hugo", "Hugo has a tent for two people (MSR Hubba Hubba),\n" +
                "a gas stove,\n" +
                "a first aid kit, and\n" +
                "a headlamp with him.\n",
        "anna", "Anna owns a sleeping bag with a comfort rating of -5 °C,\n" +
                "a sleeping pad (Therm-a-Rest NeoAir),\n" +
                "trekking poles, and a bivy sack.\n",
        "ben", "Ben is bringing a hammock,\n" +
               "a 3x3m tarp,\n" +
               "a spirit stove, and\n" +
               "a water filter (Katadyn BeFree).\n"
    );
  }


  @NotNull
  private static Map<String, List<String>> dataSetC() {
    // Knowledge base: equipment of the participants
    var map = new HashMap<String, List<String>>();

    map.put("hugo", List.of(
        "Hugo has a two-person tent (MSR Hubba Hubba)",
        "he has a gas stove",
        "he has a headlamp",
        "he has a first aid kit"
    ));

    map.put("anna", List.of(
        "Anna owns a sleeping bag with a comfort rating of -5 °C",
        "a sleeping mat (Therm-a-Rest NeoAir)",
        "trekking poles",
        "a bivy sack"
    ));

    map.put("ben", List.of(
        "Ben is bringing a hammock",
        "a 3x3m tarp",
        "a spirit stove",
        "a water filter (Katadyn BeFree)"
    ));

    return map;
  }

}
