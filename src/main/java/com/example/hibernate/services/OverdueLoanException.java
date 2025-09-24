package com.example.hibernate.services;

import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;

import java.util.List;
import java.util.stream.Collectors;

public class OverdueLoanException extends RuntimeException {
  public OverdueLoanException(Member member, List<Loan> overdueLoans) {
    super("Member " + member.getEmail() + " has overdue loans: " +
        overdueLoans.stream()
            .map(l -> l.getBook().getTitle() + " (loaned " + l.getLoanDate() + ")")
            .collect(Collectors.joining(", ")));
  }
}
