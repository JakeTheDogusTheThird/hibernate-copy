package com.example.hibernate.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
  @Id
  private int id;
  @NaturalId
  private String isbn;
  @Column
  private String title;
  @Column
  private String author;
  @Column(name = "publication_year")
  private String year;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.EAGER)
  private Set<Loan> loans = new HashSet<>();

  public Book(String isbn, String title, String author, String year) {
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.year = year;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Book book)) return false;

    return id == book.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
