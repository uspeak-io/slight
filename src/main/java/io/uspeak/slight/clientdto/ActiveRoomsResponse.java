package io.uspeak.slight.clientdto;

import io.uspeak.slight.ephemeral.Room;

import java.util.List;

public record ActiveRoomsResponse(List<Room> rooms) { }
