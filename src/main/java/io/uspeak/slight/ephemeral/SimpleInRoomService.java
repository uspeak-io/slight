package io.uspeak.slight.ephemeral;

import io.uspeak.slight.core.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleInRoomService implements InRoomService {
  private final Storage<String, Room> roomStorage;
  private final Storage<Long, Participant> participantStorage;
  @Override
  public List<Participant> getParticipants(String roomId) {
    Optional<Room> maybeRoom = roomStorage.get(roomId);
    if (maybeRoom.isEmpty()) {
      return List.of();
    }
    return maybeRoom.get().participants().participants();
  }
}
