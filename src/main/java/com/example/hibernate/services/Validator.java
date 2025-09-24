package com.example.hibernate.services;

public interface Validator<T> {
  boolean isValid(T entity);
  default boolean isNonBlank(String value) {
    return value != null && !value.isBlank();
  }
}
