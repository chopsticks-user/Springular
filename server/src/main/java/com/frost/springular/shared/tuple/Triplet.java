package com.frost.springular.shared.tuple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Triplet<T1, T2, T3> {
  private T1 first;
  private T2 second;
  private T3 third;

  public static <T1, T2, T3> Triplet<T1, T2, T3> of(
      T1 first, T2 second, T3 third) {
    return new Triplet<>(first, second, third);
  }
}
