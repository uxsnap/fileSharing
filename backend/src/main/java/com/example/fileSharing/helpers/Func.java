package com.example.fileSharing.helpers;

@FunctionalInterface
public interface Func<T, R> {
  R calculate(T param);
}
