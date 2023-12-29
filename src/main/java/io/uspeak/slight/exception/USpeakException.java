package io.uspeak.slight.exception;

import java.io.Serial;
import java.text.MessageFormat;

public class USpeakException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = -7034897190745766939L;

  public USpeakException(Throwable cause) {
    super(cause);
  }

  public USpeakException(String message) {
    super(message);
  }
}
