package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.BookDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceTest {
  @Mock
  private BookDao bookDao;

  private BookService bookService;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    BookValidator bookValidator = new BookValidator();
    bookService = new BookService(bookDao, bookValidator);
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  void getBook_returnsBookById() {
    Book book = new Book("0-306-40615-2", "Title", "Author", "2022");
    int id = 1;
    book.setId(id);
    when(bookDao.findById(id)).thenReturn(book);

    Book foundBook = bookService.getBookById(id);
    assertEquals(book, foundBook);
  }

  @Test
  void createBook_ValidBook_returnsBook() {
    Book book = new Book("0-306-40615-2", "Title", "Author", "2022");
    when(bookDao.save(book)).thenReturn(book);
    Book savedBook = bookService.createBook(book);
    assertEquals(book, savedBook);
  }

  @Test
  void createBook_InvalidBook_returnsNull() {
    Book book = new Book("0", "Title", "Author", "2022");
    when(bookDao.save(book)).thenReturn(book);
    Book savedBook = bookService.createBook(book);
    assertNull(savedBook);
  }

  @Test
  void updateBook_ValidBook_returnsBook() {
    Book book = new Book("0-306-40615-2", "Title", "Author", "2022");
    when(bookDao.update(book)).thenReturn(book);
    Book updatedBook = bookService.updateBook(book);
    assertEquals(book, updatedBook);
  }

  @Test
  void updateBook_InvalidBook_returnsNull() {
    Book book = new Book("0-306-40615-3", "Title", "Author", "2022");
    when(bookDao.update(book)).thenReturn(book);
    Book updatedBook = bookService.updateBook(book);
    assertNull(updatedBook);
  }

  @Test
  void deleteBook_verifyDeleteById() {
    int id = 1;
    bookService.deleteBook(id);
    verify(bookDao).deleteById(id);
  }

  @Test
  void searchBooks_returnsBooks() {
    String keyword = "Title";
    List<Book> expectedBooks = List.of(
        new Book("0-306-40615-2", "Title1", "Author", "2022"),
        new Book("0-306-40615-2", "Title2", "Author", "2022"),
        new Book("0-306-40615-2", "Title3", "Author", "2022")
    );
    when(bookDao.searchByKeyword(keyword)).thenReturn(expectedBooks);

    List<Book> books;
    books = bookService.searchBooks(keyword);
    assertEquals(books, expectedBooks);
  }

  @Test
  void listBooks_returnsAllBooks() {
    List<Book> expectedBooks = List.of(
        new Book("0-306-40615-2", "Title1", "Author", "2022"),
        new Book("0-306-40615-2", "Title2", "Author", "2022"),
        new Book("0-306-40615-2", "Title3", "Author", "2022")
    );

    when(bookDao.findAll()).thenReturn(expectedBooks);
    List<Book> books = bookService.listBooks();
    assertEquals(expectedBooks, books);
  }
}
