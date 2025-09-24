package com.example.hibernate.services;

import com.example.hibernate.models.Loan;

import java.time.LocalDate;
import java.util.Objects;

public class LoanValidator implements Validator<Loan> {
  @Override
  public boolean isValid(Loan loan) {
    if (loan == null) {
      return false;
    }

    LocalDate loanDate = loan.getLoanDate();
    LocalDate returnDate = loan.getReturnDate();

    return Objects.nonNull(loan.getBook())
        && Objects.nonNull(loan.getMember())
        && Objects.nonNull(loan.getLoanDate());
  }
}
