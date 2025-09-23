package com.example.hibernate.services;

public interface Validator<T> {
  boolean isValid(T entity);
}
