package io.uspeak.slight.ephemeral;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class RealtimeMessageService {
  private static final String ROOT_TOPIC_PATH = "/topic";

  private final SimpMessagingTemplate simpMessagingTemplate;
  public <T> void broadcastJoiningParticipant(String roomId, T payload) {
    String path = getPath(RealtimeComponent.ROOM, roomId);
    RealtimeMessageResponse<T> responseMessage = constructResponse(RealtimeComponent.ROOM, "participants", "join", payload);
    log.info("Broadcast participant: {} joining room: {} to path: {}", responseMessage, roomId, path);
    simpMessagingTemplate.convertAndSend(path, responseMessage);
  }

  public <T> void broadcastLeavingParticipant(String roomId, T payload) {
    String path = getPath(RealtimeComponent.ROOM, roomId);
    RealtimeMessageResponse<T> responseMessage = constructResponse(RealtimeComponent.ROOM, "participants", "leave", payload);
    log.info("Broadcast participant: {} leaving room: {} to path: {}", responseMessage, roomId, path);
    simpMessagingTemplate.convertAndSend(path, responseMessage);
  }


  public <T> void broadcastRoomCreated(T payload) {
    String path = getPath(RealtimeComponent.ROOMS, "");
    RealtimeMessageResponse<T> responseMessage = constructResponse(RealtimeComponent.ROOMS, "room", "create", payload);
    log.info("Broadcast new room created: {} to path: {}", responseMessage, path);
    simpMessagingTemplate.convertAndSend(path, responseMessage);
  }

  public <T> void broadcastRoomEnd(T payload) {
    String path = getPath(RealtimeComponent.ROOMS, "");
    RealtimeMessageResponse<T> responseMessage = constructResponse(RealtimeComponent.ROOMS, "room", "end", payload);
    log.info("Broadcast room ended: {} to path: {}", responseMessage, path);
    simpMessagingTemplate.convertAndSend(path, responseMessage);
  }

  private <T> RealtimeMessageResponse<T>  constructResponse(RealtimeComponent component, String type, String command, T payload) {
    return new RealtimeMessageResponse<>(component, type, command, payload, Instant.now());
  }

  private String getPath(RealtimeComponent component, String pathVar) {
    return String.format("%s/%s/%s", ROOT_TOPIC_PATH, component.getName(), pathVar);
  }

}
