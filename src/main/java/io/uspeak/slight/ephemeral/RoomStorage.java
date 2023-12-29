package io.uspeak.slight.ephemeral;

import io.uspeak.slight.exception.USpeakException;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RoomStorage extends AbstractStorage<String, Room> {
  private final RMap<String, Room> map;
  public RoomStorage(RedissonClient client, String name) {
    super(client, name);
    this.map = client.getMap(name);
  }

  @Override
  public void put(String s, Room value) throws USpeakException {
    boolean inserted = map.fastPutIfAbsent(s, value);
    if (!inserted) {
      throw new USpeakException(MessageFormat.format("The key: {0} already exists in name: {1}", s, name));
    }
  }

  @Override
  public Room replace(String s, Room newValue) throws USpeakException {
    if (!map.containsKey(s)) {
      throw new USpeakException(MessageFormat.format("The room: {0} does not exist in name: {1}", s, name));
    }
    Room room = map.get(s);
    this.map.fastPutIfExists(s, newValue);
    return room;
  }

  @Override
  public Optional<Room> get(String s) {
    Room room = map.get(s);
    if (room == null) return Optional.empty();
    return Optional.of(room);
  }

  @Override
  public Room delete(String s) throws USpeakException {
    if (map.containsKey(s)) {
      Room room = map.get(s);
      map.fastRemove(s);
      return room;
    } else {
      throw new USpeakException(MessageFormat.format("Room with ID: {0} does not exist in name: {1}", s, name));
    }
  }

  @Override
  public boolean contains(String s) {
    return this.map.containsKey(s);
  }

  @Override
  public Set<Map.Entry<String, Room>> entries() {
    return this.map.entrySet();
  }
}
