package com.frost.springular.shared.util.stream;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.frost.springular.shared.util.tuple.Pair;

public final class StreamHelper {
  @SuppressWarnings("unchecked")
  public static <T1, T2> Stream<Pair<T1, T2>> zip(
      Stream<? extends T1> s1, Stream<? extends T2> s2) {
    Objects.requireNonNull(s1);
    Objects.requireNonNull(s2);

    var i1 = (Iterator<T1>) s1.iterator();
    var i2 = (Iterator<T2>) s2.iterator();

    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(new Iterator<Pair<T1, T2>>() {
          @Override
          public boolean hasNext() {
            return i1.hasNext() && i2.hasNext();
          }

          @Override
          public Pair<T1, T2> next() {
            return Pair.of(i1.next(), i2.next());
          }
        }, Spliterator.ORDERED),
        s1.isParallel() || s2.isParallel());
  }
}
