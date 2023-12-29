package io.uspeak.slight.ephemeral;

import io.uspeak.slight.clientdto.RoomConfigRequest;
import lombok.Builder;

@Builder(toBuilder = true)
public record RoomCreationInfo(String id, Long userId, String topic, Integer size) {
  public static RoomCreationInfo getFromConfig(String id, RoomConfigRequest config) {
    return new RoomCreationInfo(id, config.userId(), config.topic(), config.size());
  }
}
