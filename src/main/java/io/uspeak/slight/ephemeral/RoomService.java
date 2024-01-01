package io.uspeak.slight.ephemeral;

import java.util.List;

public interface RoomService {
  Room create(RoomCreationInfo creationInfo);
  Participant join(String roomId, Long userId);
  Participant leave(String roomId, Long userId);
  List<Room> getActiveRooms();
  void clear();
}
