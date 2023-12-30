package io.uspeak.slight.controller;

import com.google.common.base.Preconditions;
import io.uspeak.slight.clientdto.ActiveRoomsResponse;
import io.uspeak.slight.clientdto.ParticipantsResponse;
import io.uspeak.slight.clientdto.RoomConfigRequest;
import io.uspeak.slight.ephemeral.InRoomService;
import io.uspeak.slight.ephemeral.Participant;
import io.uspeak.slight.ephemeral.Room;
import io.uspeak.slight.ephemeral.RoomCreationInfo;
import io.uspeak.slight.ephemeral.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
  private final RoomService roomService;
  private final InRoomService inRoomService;

  @GetMapping("/")
  public ResponseEntity<ActiveRoomsResponse> getActiveRooms() {
    List<Room> rooms = roomService.getActiveRooms();
    ActiveRoomsResponse activeRoomsResponse = new ActiveRoomsResponse(rooms);
    return ResponseEntity.ok(activeRoomsResponse);
  }

  @PostMapping("/create")
  public ResponseEntity<Room> createRoom(@RequestBody RoomConfigRequest config) {
    Preconditions.checkNotNull(config);
    String id = UUID.randomUUID().toString();
    RoomCreationInfo roomCreationInfo = RoomCreationInfo.getFromConfig(id, config);
    Room room = this.roomService.create(roomCreationInfo);
    return ResponseEntity.ok(room);
  }

  @PostMapping("/join")
  public void joinRoom(@RequestParam("roomId") String roomId, @RequestParam("userId") Long userId) {
    Preconditions.checkNotNull(roomId);
    Preconditions.checkNotNull(userId);
    this.roomService.join(roomId, userId);
  }

  @PostMapping("/leave")
  public void leaveRoom(@RequestParam("roomId") String roomId, @RequestParam("userId") Long userId) {
    Preconditions.checkNotNull(roomId);
    Preconditions.checkNotNull(userId);
    this.roomService.leave(roomId, userId);
  }

  @PostMapping("/clear")
  public void clear() {
    this.roomService.clear();
  }

  @GetMapping("/{roomId}/participants")
  public ResponseEntity<ParticipantsResponse> getParticipants(@PathVariable("roomId") String roomId) {
    Preconditions.checkNotNull(roomId);
    List<Participant> participants = this.inRoomService.getParticipants(roomId);
    ParticipantsResponse participantsResponse = new ParticipantsResponse(participants);
    return ResponseEntity.ok(participantsResponse);
  }
}
