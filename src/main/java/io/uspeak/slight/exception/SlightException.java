package io.uspeak.slight.exception;

import java.io.Serial;

public class SlightException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = -7034897190745766939L;

  public SlightException(Throwable cause) {
    super(cause);
  }

  public SlightException(String message) {
    super(message);
  }
}
