package io.uspeak.slight.ephemeral;

import io.uspeak.slight.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record Room(String id, Long createdBy, Long host, Integer size, String topic, LocalDateTime createdAt, List<Participant> participants) {
  public static Room fromRoomInfo(RoomCreationInfo roomCreationInfo) {
    Long createdBy = roomCreationInfo.userId();
    Long host = roomCreationInfo.userId();
    String topic = roomCreationInfo.topic();
    return new Room(roomCreationInfo.id(), createdBy, host, roomCreationInfo.size(), topic, LocalDateTime.now(), List.of());
  }

}
