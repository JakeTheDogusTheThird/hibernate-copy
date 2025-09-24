package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.repositories.BookDao;

import java.util.List;
import java.util.Set;

public class BookService {
  private final BookDao bookDao;
  private final BookValidator bookValidator;

  public BookService(BookDao bookDao, BookValidator bookValidator) {
    this.bookDao = bookDao;
    this.bookValidator = bookValidator;
  }

  public Book createBook(Book book) {
    if (!bookValidator.isValid(book)) {
      return null;
    }
    return this.bookDao.save(book);
  }

  public Book getBookById(int id) {
    return this.bookDao.findById(id);
  }

  public Book updateBook(Book book) {
    if (!bookValidator.isValid(book)) {
      return null;
    }
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

  public List<Book> getBookCopies(String isbn) {
    return this.bookDao.findAvailableCopiesByIsbn(isbn);
  }
}
