package io.uspeak.slight.ephemeral;

import java.util.List;

public interface InRoomService {
  List<Participant> getParticipants(String roomId);
}
