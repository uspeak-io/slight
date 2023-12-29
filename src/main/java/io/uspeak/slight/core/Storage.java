package io.uspeak.slight.core;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.uspeak.slight.exception.USpeakException;

import java.util.Optional;

public interface Storage<ID, T> {
  void put  (ID id, T value) throws USpeakException;
  @CanIgnoreReturnValue
  T replace(ID id, T newValue) throws USpeakException;
  Optional<T> get(ID id);
  T delete(ID id) throws USpeakException;
  boolean contains(ID id);
}
