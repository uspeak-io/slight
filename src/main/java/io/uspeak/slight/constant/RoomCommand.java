package io.uspeak.slight.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomCommand {
  CREATE("create"),
  JOIN("join"),
  LIST_ROOMS("list"),
  CLEAR_ALL("clearAll"),
  LIST_PARTICIPANTS("listParticipants"),
  LEAVE("leave");

  private final String val;
}
