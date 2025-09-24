package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoanValidatorTest {
  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    LoanValidator loanValidator = new LoanValidator();
    Book book = new Book("978-1-60309-502-0", "Animal Stories", "Maria Hoey", "2022");
    Member member = new Member("John", "Doe", "john@mail.com");
    Loan loan = new Loan(
        book,
        member,
        LocalDate.of(2025, 9, 24),
        LocalDate.of(2025, 9, 25)
    );

    boolean result = loanValidator.isValid(loan);
    assertTrue(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullLoan(Loan loan) {
    LoanValidator loanValidator = new LoanValidator();
    boolean result = loanValidator.isValid(loan);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullBook(Book book) {
    LoanValidator loanValidator = new LoanValidator();
    Member member = new Member("John", "Doe", "john@mail.com");
    Loan loan = new Loan(
        book,
        member,
        LocalDate.of(2025, 9, 24),
        LocalDate.of(2025, 9, 25)
    );
    boolean result = loanValidator.isValid(loan);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullMember(Member member) {
    LoanValidator loanValidator = new LoanValidator();
    Book book = new Book("978-1-60309-502-0", "Animal Stories", "Maria Hoey", "2022");
    Loan loan = new Loan(
        book,
        member,
        LocalDate.of(2025, 9, 24),
        LocalDate.of(2025, 9, 25)
    );

    boolean result = loanValidator.isValid(loan);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullLoan(LocalDate loanDate) {
    LoanValidator loanValidator = new LoanValidator();
    Book book = new Book("978-1-60309-502-0", "Animal Stories", "Maria Hoey", "2022");
    Member member = new Member("John", "Doe", "john@mail.com");
    Loan loan = new Loan(
        book,
        member,
        loanDate,
        LocalDate.of(2025, 9, 25)
    );
    boolean result = loanValidator.isValid(loan);
    assertFalse(result);
  }
}
