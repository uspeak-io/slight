package io.uspeak.slight.ephemeral;

import io.uspeak.slight.exception.USpeakException;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.text.MessageFormat;
import java.util.Optional;

public class ParticipantStorage extends AbstractStorage<Long, Participant> {
  private final RMap<Long, Participant> map;
  public ParticipantStorage(RedissonClient client, String name) {
    super(client, name);
    this.map = client.getMap(name);
  }

  @Override
  public void put(Long aLong, Participant value) throws USpeakException {
    boolean inserted = map.fastPutIfAbsent(aLong, value);
    if (!inserted) {
      throw new USpeakException(MessageFormat.format("The key: {0} already exists in name: {1}", aLong, name));
    }
  }

  @Override
  public Participant replace(Long aLong, Participant newValue) throws USpeakException {
    if (!map.containsKey(aLong)) {
      throw new USpeakException(MessageFormat.format("The key: {0} does not exist in name: {1}", aLong, name));
    }
    Participant participant = map.get(aLong);
    this.map.fastPutIfExists(aLong, newValue);
    return participant;
  }

  @Override
  public Optional<Participant> get(Long aLong) {
    Participant participant = map.get(aLong);
    if (participant != null) {
      return Optional.of(participant);
    }
    return Optional.empty();
  }

  @Override
  public Participant delete(Long aLong) throws USpeakException {
    if (!map.containsKey(aLong)) {
      throw new USpeakException(MessageFormat.format("The key: {0} does not exist in name: {1}", aLong, name));
    }
    Participant participant = this.map.get(aLong);
    map.remove(aLong);
    return participant;
  }

  @Override
  public boolean contains(Long aLong) {
    return map.containsKey(aLong);
  }
}
