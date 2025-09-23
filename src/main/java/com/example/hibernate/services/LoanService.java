package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.BookDao;
import com.example.hibernate.repositories.LoanDao;
import com.example.hibernate.repositories.MemberDao;

import java.time.LocalDate;
import java.util.List;

public class LoanService {
  private final LoanDao loanDao;
  private final BookDao bookDao;
  private final MemberDao memberDao;

  public LoanService(LoanDao loanDao, BookDao bookDao, MemberDao memberDao) {
    this.bookDao = bookDao;
    this.memberDao = memberDao;
    this.loanDao = loanDao;
  }

  public Loan loanBook(String isbn, String email) {
    List<Book> available = this.bookDao.findAvailableCopiesByIsbn(isbn);
    if (available.isEmpty()) {
      throw new IllegalStateException("No available copies of this book.");
    }
    Book book = available.getFirst();
    Member member = this.memberDao.findByEmail(email);
    Loan loan = new Loan(book, member, LocalDate.now(), null);
    this.loanDao.save(loan);
    return loan;
  }

  public void returnBook(int id) {
    Loan loan = this.loanDao.findById(id);
    loan.setReturnDate(LocalDate.now());
    this.loanDao.save(loan);
  }

  public List<Loan> listAll() {
    return this.loanDao.findAll();
  }
}
