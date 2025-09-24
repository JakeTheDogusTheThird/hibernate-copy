package com.example.hibernate.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

  @Test
  void equals_ShouldReturnTrueForSameId() {
    Book book1 = new Book();
    Book book2 = new Book();
    book1.setId(1);
    book2.setId(1);

    boolean result = book1.equals(book2);
    assertTrue(result);
  }

  @Test
  void equals_ShouldReturnFalseForDifferentId() {
    Book book1 = new Book();
    Book book2 = new Book();
    book1.setId(1);
    book2.setId(2);
    boolean result = book1.equals(book2);
    assertFalse(result);
  }

  @Test
  void equals_ShouldReturnFalseForNonBookInstance() {
    Book book1 = new Book();
    Object book2 = new Object();
    boolean result = book1.equals(book2);
    assertFalse(result);
  }

  @Test
  void hashCode_ShouldReturnHashCode() {
    Book book1 = new Book();
    Book book2 = new Book();
    book1.setId(1);
    book2.setId(1);
    assertEquals(book1.hashCode(), book2.hashCode());
  }
}