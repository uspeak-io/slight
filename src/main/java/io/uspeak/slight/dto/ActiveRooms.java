package io.uspeak.slight.dto;

import io.uspeak.slight.core.SortableContainer;
import io.uspeak.slight.ephemeral.Room;

public record ActiveRooms(SortableContainer<Room> rooms) { }
