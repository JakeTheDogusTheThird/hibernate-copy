package com.example.hibernate.services;

public class NoAvailableCopiesException extends RuntimeException {
  public NoAvailableCopiesException(String isbn) {
    super("No available copies for ISBN " + isbn);
  }
}
