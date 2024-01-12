package io.uspeak.slight.ephemeral;

import io.uspeak.slight.core.InRoomMessageType;

public interface ChatMessageService {
  void sendMessage(String channelId, InRoomMessageType messageType, byte[] message);
}
