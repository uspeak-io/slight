package io.uspeak.slight.ephemeral;

import io.uspeak.slight.core.Storage;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;

@AllArgsConstructor
public abstract class AbstractStorage<ID, T> implements Storage<ID, T> {
  protected final RedissonClient client;
  protected final String name;
}
