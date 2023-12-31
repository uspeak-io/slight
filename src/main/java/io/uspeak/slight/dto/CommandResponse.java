package io.uspeak.slight.dto;

import io.uspeak.slight.constant.RoomCommand;

import java.time.Instant;

public record CommandResponse<T> (T payload, RoomCommand command, Instant timestamp) { }
