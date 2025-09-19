package com.example.hibernate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
  @Column
  private LocalDate loanDate;
  @Column
  private LocalDate returnDate;

  public Loan(Member member, Book book, LocalDate loanDate, LocalDate returnDate) {
    this.member = member;
    this.book = book;
    this.loanDate = loanDate;
    this.returnDate = returnDate;
  }
}
