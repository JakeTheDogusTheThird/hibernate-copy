import com.example.hibernate.models.Book;
import com.example.hibernate.services.BookValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookValidatorTest {
  @ParameterizedTest
  @ValueSource(strings = {"0-306-40615-2", "978-1-60309-502-0", "0-8044-2957-X"})
  void isValid_ShouldReturnTrueForAllConditionsMet(String isbn) {
    BookValidator bookValidator = new BookValidator();
    Book book = new Book(isbn, "Animal Stories", "Maria Hoey", "2022");
    boolean result = bookValidator.isValid(book);
    assertTrue(result);
  }

  @ParameterizedTest
  @ValueSource(strings = {"",
      "978-1-60309-502-1",
      "0-306-40615-3", "123",
      "978-1-A0309-502-1",
      "0-306-4b615-3",
      "0-306-40615-C"})
  void isValid_ShouldReturnFalseForInvalidIsbn(String isbn) {
    BookValidator bookValidator = new BookValidator();
    Book book = new Book(isbn, "Animal Stories", "Maria Hoey", "2022");
    boolean result = bookValidator.isValid(book);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullIsbn(String isbn) {
    BookValidator bookValidator = new BookValidator();
    Book book = new Book(isbn, "Animal Stories", "Maria Hoey", "2022");
    boolean result = bookValidator.isValid(book);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void isValid_ShouldReturnFalseForNullAndEmptyTitle(String title) {
    BookValidator bookValidator = new BookValidator();
    Book book = new Book("0-306-40615-2", title, "Maria Hoey", "2022");
    boolean result = bookValidator.isValid(book);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void isValid_ShouldReturnFalseForNullAndEmptyAuthor(String author) {
    BookValidator bookValidator = new BookValidator();
    Book book = new Book("0-306-40615-2", "Animal Stories", author, "2022");
    boolean result = bookValidator.isValid(book);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void isValid_ShouldReturnFalseForEmptyYear(String year) {
    BookValidator bookValidator = new BookValidator();
    Book book = new Book("0-306-40615-2", "Animal Stories", "Maria Hoey", year);
    boolean result = bookValidator.isValid(book);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullBook(Book book) {
    BookValidator bookValidator = new BookValidator();
    boolean result = bookValidator.isValid(book);
    assertFalse(result);
  }
}

