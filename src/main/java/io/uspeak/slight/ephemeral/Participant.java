package io.uspeak.slight.ephemeral;

import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

public record Participant(String roomId, Long userId, LocalDateTime joinedAt) {
}
