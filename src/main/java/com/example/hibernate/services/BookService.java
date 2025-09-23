package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.repositories.BookDao;

import java.util.List;
import java.util.Set;

public class BookService {
  private final BookDao bookDao;

  public BookService(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  public Book addBook(Book book) {
    return this.bookDao.save(book);
  }

  public Book findBook(int id) {
    return this.bookDao.findById(id);
  }

  public Book updateBook(Book book) {
    return this.bookDao.update(book);
  }

  public void deleteBook(int id) {
    this.bookDao.deleteById(id);
  }

  public List<Book> listBooks() {
    return this.bookDao.findAll();
  }

  public List<Book> searchBooks(String keyword) {
    return this.bookDao.searchByKeyword(keyword);
  }

  public Set<Loan> getLoanHistory(int id) {
    return this.bookDao.findById(id).getLoans();
  }

}
