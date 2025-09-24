package com.example.hibernate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  private Member member;
  @ManyToOne
  private Book book;
  @Column(name = "loan_date")
  private LocalDate loanDate;
  @Setter(AccessLevel.NONE)
  @Column(name = "return_date")
  private LocalDate returnDate;

  public Loan(Book book, Member member, LocalDate loanDate, LocalDate returnDate) {
    this.member = member;
    this.book = book;
    this.loanDate = loanDate;
    this.returnDate = returnDate;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Loan loan)) return false;

    return id == loan.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  public void setReturnDate(LocalDate returnDate) {
    if (returnDate.isBefore(this.loanDate)) {
      throw new IllegalArgumentException("Return Date cannot be before Return Date");
    }
    this.returnDate = returnDate;
  }
}
