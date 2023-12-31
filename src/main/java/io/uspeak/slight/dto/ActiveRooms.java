package io.uspeak.slight.dto;

import io.uspeak.slight.ephemeral.Room;

import java.util.List;

public record ActiveRooms(List<Room> rooms) { }
