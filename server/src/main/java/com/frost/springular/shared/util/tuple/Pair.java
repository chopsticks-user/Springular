package com.frost.springular.shared.util.tuple;

import java.util.function.BiConsumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pair<T1, T2> {
  private T1 first;
  private T2 second;

  public static <T1, T2> Pair<T1, T2> of(T1 first, T2 second) {
    return new Pair<>(first, second);
  }

  public void apply(BiConsumer<T1, T2> consumer) {
    consumer.accept(first, second);
  }
}
