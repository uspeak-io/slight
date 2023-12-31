package io.uspeak.slight.ephemeral;

import io.uspeak.slight.Participants;

public interface InRoomService {
  Participants getParticipants(String roomId);
}
