package io.uspeak.slight.core;

@FunctionalInterface
public interface EventListener<T> {
  void onListen(T eventPayload);
}
