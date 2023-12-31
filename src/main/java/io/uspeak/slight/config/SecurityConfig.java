package io.uspeak.slight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
    return http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
        .cors(cors -> {
          cors.configurationSource(corsConfigurationSource);
        })
        .csrf(AbstractHttpConfigurer::disable).build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of("*"));
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.setAllowedMethods(List.of("*"));
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return urlBasedCorsConfigurationSource;
  }
}
