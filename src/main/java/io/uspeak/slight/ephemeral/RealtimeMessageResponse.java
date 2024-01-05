package io.uspeak.slight.ephemeral;

import lombok.Data;

import java.time.Instant;

@Data
public class RealtimeMessageResponse<T>{
  private RealtimeComponent component;
  private String type;
  private String command;
  private T payload;
  private Instant timestamp;
  public RealtimeMessageResponse(RealtimeComponent component, String type, String command, T payload, Instant timestamp) {
    this.component = component;
    this.type = type;
    this.command = command;
    this.payload = payload;
    this.timestamp = timestamp;
  }
}
