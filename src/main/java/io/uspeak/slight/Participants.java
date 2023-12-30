package io.uspeak.slight;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.uspeak.slight.ephemeral.Participant;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Participants {
  private final Set<Participant> participants = new LinkedHashSet<>();

  public void add(Participant participant) {
    this.participants.add(participant);
  }

  public Participants remove(Participant participant) {
    Set<Participant> set = new LinkedHashSet<>(participants);
    set.remove(participant);
    Participants p = new Participants();
    p.participants.addAll(set);
    return p;
  }
  @JsonProperty
  public List<Participant> participants() {
    return new ArrayList<>(participants);
  }

  public static Participants ofEmpty() {
    return new Participants();
  }

  public static Participants of(List<Participant> participants) {
    Participants p = new Participants();
    p.participants.addAll(participants);
    return p;
  }
}
