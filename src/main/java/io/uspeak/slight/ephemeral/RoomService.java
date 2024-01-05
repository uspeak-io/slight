package io.uspeak.slight.ephemeral;

import io.uspeak.slight.core.SortableContainer;

public interface RoomService {
  Room create(RoomCreationInfo creationInfo);
  Participant join(String roomId, Long userId, String displayName);
  Participant leave(String roomId, Long userId);
  SortableContainer<Room> getActiveRooms();
  void clear();
}
