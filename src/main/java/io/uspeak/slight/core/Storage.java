package io.uspeak.slight.core;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.uspeak.slight.exception.SlightException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Storage<ID, T> {
  void put(ID id, T value) throws SlightException;

  @CanIgnoreReturnValue
  T replace(ID id, T newValue) throws SlightException;

  Optional<T> get(ID id);

  @CanIgnoreReturnValue
  T delete(ID id) throws SlightException;

  boolean contains(ID id);

  Set<Map.Entry<ID, T>> entries();
}
