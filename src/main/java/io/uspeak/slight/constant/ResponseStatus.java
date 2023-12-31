package io.uspeak.slight.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatus {
  OK("ok"),
  ERROR("error");
  private final String status;
}
