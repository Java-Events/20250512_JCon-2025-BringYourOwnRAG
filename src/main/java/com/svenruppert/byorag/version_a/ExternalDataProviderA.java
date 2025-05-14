package com.svenruppert.byorag.version_a;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExternalDataProviderA {
  private ExternalDataProviderA() {
  }

  @NotNull
  public static List<Person> externalData() {
    return dataSetB();
  }

  @NotNull
  private static List<Person> dataSetA() {
    var hugo = new Person("Hugo", new HashSet<>());
    hugo.info().add("Hugo has a two-person tent (MSR Hubba Hubba)");
    hugo.info().add("has a gas stove");
    hugo.info().add("a headlamp");
    hugo.info().add("and a first aid kit");

    var anna = new Person("Anna", new HashSet<>());
    anna.info().add("Anna owns a sleeping bag rated for -5°C comfort");
    anna.info().add("a sleeping mat (Therm-a-Rest NeoAir)");
    anna.info().add("trekking poles");
    anna.info().add("a bivy sack");

    var ben = new Person("Ben", new HashSet<>());
    ben.info().add("Ben is bringing a hammock");
    ben.info().add("a 3x3m tarp");
    ben.info().add("a spirit stove");
    ben.info().add("a water filter (Katadyn BeFree)");

    return List.of(hugo, anna, ben);
  }

  @NotNull
  private static List<Person> dataSetB() {
    var hugo = new Person("Hugo", new HashSet<>());
    hugo.info().add("he has a two-person tent (MSR Hubba Hubba)");
    hugo.info().add("he has a gas stove");
    hugo.info().add("he has a headlamp");
    hugo.info().add("he has a first aid kit");

    var anna = new Person("Anna", new HashSet<>());
    anna.info().add("she owns a sleeping bag rated for -5°C comfort");
    anna.info().add("she has a sleeping mat (Therm-a-Rest NeoAir)");
    anna.info().add("she uses trekking poles");
    anna.info().add("she has a bivy sack");

    var ben = new Person("Ben", new HashSet<>());
    ben.info().add("has a hammock");
    ben.info().add("has a 3x3m tarp");
    ben.info().add("has a spirit stove");
    ben.info().add("has a water filter (Katadyn BeFree)");

    return List.of(hugo, anna, ben);
  }

  public record Person(String name, Set<String> info) {
  }
}

