package io.uspeak.slight.controller;

import com.google.common.base.Preconditions;
import io.uspeak.slight.Participants;
import io.uspeak.slight.dto.ActiveRooms;
import io.uspeak.slight.dto.CommandResponse;
import io.uspeak.slight.dto.RoomConfigRequest;
import io.uspeak.slight.ephemeral.Participant;
import io.uspeak.slight.ephemeral.Room;
import io.uspeak.slight.facade.RoomGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
  private final RoomGateway roomGateway;

  @GetMapping("/")
  public CommandResponse<ActiveRooms> getActiveRooms() {
    return roomGateway.getActiveRooms();
  }

  @PostMapping("/create")
  public CommandResponse<Room> createRoom(@RequestBody RoomConfigRequest config) {
    return roomGateway.createRoom(config);
  }

  @PostMapping("/join")
  public CommandResponse<Participant> joinRoom(@RequestParam("roomId") String roomId, @RequestParam("userId") Long userId, @RequestParam("displayName") String displayName) {
    return roomGateway.joinRoom(roomId, userId, displayName);
  }

  @PostMapping("/leave")
  public CommandResponse<Participant> leaveRoom(@RequestParam("roomId") String roomId, @RequestParam("userId") Long userId) {
    return roomGateway.leaveRoom(roomId, userId);
  }

  @PostMapping("/end")
  public CommandResponse<Room> endRoom(@RequestParam("roomId") String roomId, @RequestParam("userId") Long userId) {
    return roomGateway.endRoom(roomId, userId);
  }

  @PostMapping("/clear")
  public CommandResponse<String> clear() {
    return this.roomGateway.clear();
  }

  @GetMapping("/{roomId}/participants")
  public CommandResponse<Participants> getParticipants(@PathVariable("roomId") String roomId) {
    return roomGateway.getParticipants(roomId);
  }
}
