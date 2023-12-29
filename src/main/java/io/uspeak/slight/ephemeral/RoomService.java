package io.uspeak.slight.ephemeral;

import java.util.List;

public interface RoomService {
  Room create(RoomCreationInfo creationInfo);
  void join(String roomId, Long userId);
  void leave(String roomId, Long userId);
  List<Room> getActiveRooms();
}
