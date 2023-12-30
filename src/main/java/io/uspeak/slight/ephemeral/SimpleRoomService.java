package io.uspeak.slight.ephemeral;

import io.uspeak.slight.Participants;
import io.uspeak.slight.core.Storage;
import io.uspeak.slight.exception.SlightException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SimpleRoomService implements RoomService {
  private final Storage<String, Room> roomStorage;
  private final Storage<Long, Participant> participantStorage;

  @Override
  public Room create(RoomCreationInfo creationInfo) {
    String id = creationInfo.id();
    if (roomStorage.contains(id)) {
      throw new SlightException(MessageFormat.format("Room with id: {0} already exists", id));
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
          throw new SlightException(MessageFormat.format("User with id: {0}, already in room: {1}", userId, e.roomId()));
        } else {
          throw new SlightException(MessageFormat.format("User with id: {0}, already in the current room: {1}", userId, roomId));
        }
      });
    } else {
      Optional<Room> maybeRoom = roomStorage.get(roomId);
      Room room = maybeRoom.orElseThrow(() -> new SlightException(MessageFormat.format("The room with id: {0} does not exist", roomId)));
      Participants participants = room.participants();
      boolean isHost = isHost(userId, roomId);
      Participant participant = new Participant(roomId, userId, isHost, Instant.now());
      participants.add(participant);
      Room updatedRoom = room.toBuilder().participants(participants).build();
      roomStorage.replace(roomId, updatedRoom);
      addNewOrReplaceParticipant(userId, participant);
    }
  }

  private void addNewOrReplaceParticipant(Long userId, Participant participant) {
    if (participantStorage.contains(userId))  {
      participantStorage.replace(userId, participant);
    } else {
      participantStorage.put(userId, participant);
    }
  }

  private boolean isHost(Long userId, String roomId) {
    Optional<Room> maybeRoom = roomStorage.get(roomId);
    if (maybeRoom.isEmpty())
      return false;
    Room room = maybeRoom.get();
    return room.host().equals(userId);
  }

  @Override
  public void leave(String roomId, Long userId) {
    Optional<Room> maybeRoom = roomStorage.get(roomId);
    Room room = maybeRoom.orElseThrow(() -> new SlightException(MessageFormat.format("The room with id: {0} does not exist", roomId)));
    Optional<Participant> maybeParticipant = participantStorage.get(userId);
    Participant participant = maybeParticipant.orElseThrow(() -> new SlightException(MessageFormat.format("Participant with id: {0} not found in room: {1}", userId, roomId)));
    if (!participant.roomId().equals(roomId)) {
      throw new SlightException(MessageFormat.format("Participant with id: {0} is in different room", userId));
    }
    Participants participants = room.participants().remove(participant);
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
    return entries.stream().map(Map.Entry::getValue).toList();
  }

  @Override
  public void clear() {
    Set<Map.Entry<Long, Participant>> participantEntries = participantStorage.entries();
    for(final var e : participantEntries) {
      this.participantStorage.delete(e.getKey());
    }
    Set<Map.Entry<String, Room>> roomEntries = roomStorage.entries();
    for(final var e : roomEntries) {
      this.roomStorage.delete(e.getKey());
    }
  }
}
