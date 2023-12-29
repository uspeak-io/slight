package io.uspeak.slight.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "redis")
@Component
@Data
public class RedisProperties {
  private String address;
  private String password;
}
