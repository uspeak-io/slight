package io.uspeak.slight.ephemeral;

import io.uspeak.slight.Participants;
import io.uspeak.slight.core.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleInRoomService implements InRoomService {
  private final Storage<String, Room> roomStorage;
  @Override
  public Participants getParticipants(String roomId) {
    Optional<Room> maybeRoom = roomStorage.get(roomId);
    if (maybeRoom.isEmpty()) {
      return Participants.ofEmpty();
    }
    return maybeRoom.get().participants();
  }
}
