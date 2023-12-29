package io.uspeak.slight.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class RedisConfig {
  private final RedisProperties redisProperties;

  @Bean
  RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer().setAddress(redisProperties.getAddress()).setPassword(redisProperties.getPassword());
    RedissonClient redissonClient = Redisson.create(config);
    log.info("Create redisson client with config: {}", config.toString());
    return redissonClient;
  }
}
