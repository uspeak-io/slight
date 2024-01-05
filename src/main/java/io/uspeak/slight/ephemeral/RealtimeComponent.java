package io.uspeak.slight.ephemeral;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RealtimeComponent {
  ROOMS("rooms"),
  ROOM("room");
  private final String name;

}