package io.uspeak.slight.core;

import io.uspeak.slight.ephemeral.ParticipantStorage;
import io.uspeak.slight.ephemeral.RoomStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemMappings {
  private InMemMappings() {

  }
  private static final Map<Class<?>, String> mappings = new ConcurrentHashMap<>();
  static {
    mappings.put(RoomStorage.class, "rooms");
    mappings.put(ParticipantStorage.class, "participants");
  }
  public static  String get(Class<?> clazz) {
    return mappings.getOrDefault(clazz, null);
  }

  public static void add(Class<?> clazz, String id) {
    mappings.putIfAbsent(clazz, id);
  }
}
