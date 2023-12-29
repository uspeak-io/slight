package io.uspeak.slight.ephemeral;

import io.uspeak.slight.core.Storage;
import io.uspeak.slight.exception.USpeakException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
  private final Storage<String, Room> roomStorage;
  private final Storage<Long, Participant> participantStorage;

  @Override
  public Room create(RoomCreationInfo creationInfo) {
    String id = creationInfo.id();
    if (roomStorage.contains(id)) {
      throw new USpeakException(MessageFormat.format("Room with id: {0} already exists", id));
    }
    Room room = Room.fromRoomInfo(creationInfo);
    roomStorage.put(room.id(), room);
    return room;
  }

  @Override
  public void join(String roomId, Long userId) {
    if (participantStorage.contains(userId)) {
      Optional<Participant> retrieve = participantStorage.get(userId);
      retrieve.ifPresent(e -> {
        if (!e.roomId().equals(roomId)) {
          throw new USpeakException(MessageFormat.format("User with id: {0}, already in room: {1}", userId, e.roomId()));
        }
      });
    } else {
      Optional<Room> maybeRoom = roomStorage.get(roomId);
      Room room = maybeRoom.orElseThrow(() -> new USpeakException(MessageFormat.format("The room with id: {0} does not exist", roomId)));
      List<Participant> participants = room.participants();
      participants = new ArrayList<>(participants);
      Participant participant = new Participant(roomId, userId, LocalDateTime.now());
      participants.add(participant);
      Room updatedRoom = room.toBuilder().participants(participants).build();
      roomStorage.replace(roomId, updatedRoom);
    }
  }

  @Override
  public void leave(String roomId, Long userId) {
    Optional<Room> maybeRoom = roomStorage.get(roomId);
    Room room = maybeRoom.orElseThrow(() -> new USpeakException(MessageFormat.format("The room with id: {0} does not exist", roomId)));
    List<Participant> participants = new ArrayList<>(room.participants());
    int idx = -1;
    for (int i = 0; i < participants.size(); i++) {
      Participant p = participants.get(i);
      if (p.userId().equals(userId)) {
        idx = i;
        break;
      }
    }
    if (idx == -1)
      throw new USpeakException(MessageFormat.format("Participant with id: {0} does not exist in room: {1}", userId, roomId));
    participants.remove(idx);
    room = room.toBuilder().participants(participants).build();
    roomStorage.replace(roomId, room);
    participantStorage.delete(userId);
  }

  @Override
  public List<Room> getActiveRooms() {
    Set<Map.Entry<String, Room>> entries = this.roomStorage.entries();
    if (entries.isEmpty()) {
      return List.of();
    }
    return entries
        .stream()
        .map(Map.Entry::getValue)
        .toList();
  }
}
