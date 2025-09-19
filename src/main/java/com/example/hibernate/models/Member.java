package com.example.hibernate.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
  private Collection<Loan> loans = new HashSet<>();

  public Member(String firstName, String lastName, String email, Collection<Loan> loans) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.loans = loans;
  }
}
