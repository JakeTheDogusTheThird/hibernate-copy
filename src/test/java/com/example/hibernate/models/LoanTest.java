package com.example.hibernate.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

  @Test
  void equals_ShouldReturnTrueForSameId() {
    Loan loan1 = new Loan();
    Loan loan2 = new Loan();
    loan1.setId(1);
    loan2.setId(1);

    boolean result = loan1.equals(loan2);
    assertTrue(result);
  }

  @Test
  void equals_ShouldReturnFalseForDifferentId() {
    Loan loan1 = new Loan();
    Loan loan2 = new Loan();
    loan1.setId(1);
    loan2.setId(2);
    boolean result = loan1.equals(loan2);
    assertFalse(result);
  }

  @Test
  void equals_ShouldReturnFalseForNonLoanInstance() {
    Loan loan1 = new Loan();
    Object loan2 = new Object();
    boolean result = loan1.equals(loan2);
    assertFalse(result);
  }

  @Test
  void hashCode_ShouldReturnHashCode() {
    Loan loan1 = new Loan();
    Loan loan2 = new Loan();
    loan1.setId(1);
    loan2.setId(1);
    assertEquals(loan1.hashCode(), loan2.hashCode());
  }
}