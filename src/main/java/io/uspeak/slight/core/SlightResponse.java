package io.uspeak.slight.core;

import javax.annotation.Nullable;

public record SlightResponse<T>(@Nullable T payload, ResponseStatus status, @Nullable Throwable error) {
  public static <T> SlightResponse<T> of(T payload, ResponseStatus status, Throwable error) {
    return new SlightResponse<>(payload, status, error);
  }
}
