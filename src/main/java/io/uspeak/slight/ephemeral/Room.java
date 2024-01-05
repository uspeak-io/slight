package io.uspeak.slight.ephemeral;

import io.uspeak.slight.Participants;
import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Room(String id, Long createdBy, Long host, Integer size, String topic, Instant createdAt, Participants participants) implements Comparable<Room> {
  public static Room fromRoomInfo(RoomCreationInfo roomCreationInfo) {
    Long createdBy = roomCreationInfo.userId();
    Long host = roomCreationInfo.userId();
    String topic = roomCreationInfo.topic();
    return new Room(roomCreationInfo.id(), createdBy, host, roomCreationInfo.size(), topic, Instant.now(), Participants.ofEmpty());
  }

  @Override
  public int compareTo(Room o) {
    return -1 * this.createdAt.compareTo(o.createdAt); // reverse
  }
}
