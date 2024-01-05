package io.uspeak.slight.ephemeral;

import java.time.Instant;
import java.util.Objects;

public record Participant(String roomId, Long userId, String displayName, Boolean isHost, Instant joinedAt) {
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Participant that = (Participant) o;
    return Objects.equals(roomId, that.roomId) && Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, userId);
  }
}
