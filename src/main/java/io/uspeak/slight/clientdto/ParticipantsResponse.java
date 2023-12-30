package io.uspeak.slight.clientdto;

import io.uspeak.slight.ephemeral.Participant;

import java.util.List;

public record ParticipantsResponse(List<Participant> participants) { }
