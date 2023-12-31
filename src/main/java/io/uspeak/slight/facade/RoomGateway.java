package io.uspeak.slight.facade;

import com.google.common.base.Preconditions;
import io.uspeak.slight.Participants;
import io.uspeak.slight.constant.ResponseStatus;
import io.uspeak.slight.constant.RoomCommand;
import io.uspeak.slight.dto.ActiveRooms;
import io.uspeak.slight.dto.CommandResponse;
import io.uspeak.slight.dto.RoomConfigRequest;
import io.uspeak.slight.ephemeral.InRoomService;
import io.uspeak.slight.ephemeral.Room;
import io.uspeak.slight.ephemeral.RoomCreationInfo;
import io.uspeak.slight.ephemeral.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomGateway {
  private final RoomService roomService;
  private final InRoomService inRoomService;

  public CommandResponse<ActiveRooms> getActiveRooms() {
    List<Room> rooms = roomService.getActiveRooms();
    ActiveRooms activeRooms = new ActiveRooms(rooms);
    return new CommandResponse<>(activeRooms, RoomCommand.LIST_ROOMS, Instant.now());
  }

  public CommandResponse<Room> createRoom(RoomConfigRequest config) {
    String id = UUID.randomUUID().toString();
    RoomCreationInfo roomCreationInfo = RoomCreationInfo.getFromConfig(id, config);
    Room room = this.roomService.create(roomCreationInfo);
    return new CommandResponse<>(room, RoomCommand.CREATE, Instant.now());
  }

  public CommandResponse<String> joinRoom(String roomId, Long userId) {
    this.roomService.join(roomId, userId);
    return new CommandResponse<>(ResponseStatus.OK.getStatus(), RoomCommand.JOIN, Instant.now());
  }

  public CommandResponse<String> leaveRoom(String roomId, Long userId) {
    this.roomService.leave(roomId, userId);
    return new CommandResponse<>(ResponseStatus.OK.getStatus(), RoomCommand.LEAVE, Instant.now());
  }

  public CommandResponse<String> clear() {
    this.roomService.clear();
    return new CommandResponse<>(ResponseStatus.OK.getStatus(), RoomCommand.CLEAR_ALL, Instant.now());
  }

  public CommandResponse<Participants> getParticipants(String roomId) {
    Preconditions.checkNotNull(roomId);
    Participants participants = this.inRoomService.getParticipants(roomId);
    return new CommandResponse<>(participants, RoomCommand.LIST_PARTICIPANTS, Instant.now());
  }
}