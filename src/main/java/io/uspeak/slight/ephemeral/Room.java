package io.uspeak.slight.ephemeral;

import io.uspeak.slight.Participants;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Room(String id, Long createdBy, Long host, Integer size, String topic, LocalDateTime createdAt, Participants participants) {
  public static Room fromRoomInfo(RoomCreationInfo roomCreationInfo) {
    Long createdBy = roomCreationInfo.userId();
    Long host = roomCreationInfo.userId();
    String topic = roomCreationInfo.topic();
    return new Room(roomCreationInfo.id(), createdBy, host, roomCreationInfo.size(), topic, LocalDateTime.now(), Participants.ofEmpty());
  }

}
