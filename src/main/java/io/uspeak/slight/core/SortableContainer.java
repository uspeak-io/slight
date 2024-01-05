package io.uspeak.slight.core;

import com.google.common.base.Preconditions;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class SortableContainer<T extends Comparable<T>> {
  private List<T> values;

  public SortableContainer(List<T> values) {
    Preconditions.checkNotNull(values);
    this.values = values.stream().sorted().toList();
  }

  public SortableContainer(List<T> values, Comparator<T> comparator) {
    Preconditions.checkNotNull(values);
    Preconditions.checkNotNull(comparator);
    this.values = values.stream().sorted(comparator).toList();
  }


  public static <T extends Comparable<T>> SortableContainer<T> ofEmpty() {
    return new SortableContainer<T>(List.of());
  }

}
