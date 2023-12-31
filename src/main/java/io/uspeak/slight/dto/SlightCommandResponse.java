package io.uspeak.slight.dto;

import io.uspeak.slight.constant.ResponseStatus;

public record SlightCommandResponse<T>(T payload, ResponseStatus status ){ }
