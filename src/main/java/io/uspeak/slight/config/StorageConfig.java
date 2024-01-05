package io.uspeak.slight.config;

import io.uspeak.slight.core.InMemMappings;
import io.uspeak.slight.core.Storage;
import io.uspeak.slight.ephemeral.Participant;
import io.uspeak.slight.ephemeral.ParticipantStorage;
import io.uspeak.slight.ephemeral.Room;
import io.uspeak.slight.ephemeral.RoomStorage;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {
  private final RedissonClient client;
  @Bean
  public Storage<String, Room> roomInMemStorage() {
    String name = InMemMappings.get(RoomStorage.class);
    return new RoomStorage(client, name);
  }

  @Bean
  public Storage<Long, Participant> participantStorage() {
    String name = InMemMappings.get(ParticipantStorage.class);
    return new ParticipantStorage(client, name);
  }

}
