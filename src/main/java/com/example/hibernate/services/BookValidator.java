package com.example.hibernate.services;

import com.example.hibernate.models.Book;

import java.util.Objects;

public class BookValidator implements Validator<Book>{

  private static final String ISBN13_ALLOWED_CHARACTERS = "\\d{13}";
  private static final String ISBN10_ALLOWED_CHARACTERS = "\\d{9}[\\dX]";
  public static final String YEAR_ALLOWED_CHARACTERS = "\\d{4}";

  @Override
  public boolean isValid(Book book) {
    if (book == null) {
      return false;
    }

    return isValidIsbn(book.getIsbn())
        && isNonBlank(book.getTitle())
        && isNonBlank(book.getAuthor())
        && Objects.nonNull(book.getYear()) && book.getYear().matches(YEAR_ALLOWED_CHARACTERS);
  }

  private boolean isValidIsbn(String isbn) {
    if (isbn == null || isbn.isBlank()) {
      return false;
    }

    String cleanIsbn = isbn.replace("-", "").trim();

    if (cleanIsbn.matches(ISBN13_ALLOWED_CHARACTERS)) {
      return isValidIsbn13(cleanIsbn);
    } else if (cleanIsbn.matches(ISBN10_ALLOWED_CHARACTERS)) {
      return isValidIsbn10(cleanIsbn);
    }
    return false;
  }

  private boolean isValidIsbn13(String isbn) {
    int sum = 0;
    for (int i = 0; i < 12; i++) {
      int digit = Character.getNumericValue(isbn.charAt(i));
      sum += (i % 2 == 0) ? digit : digit * 3;
    }

    char expectedCheckSum = isbn.charAt(12);
    int computedCheckSum = (10 - (sum % 10)) % 10;
    return computedCheckSum == Character.getNumericValue(expectedCheckSum);
  }

  private boolean isValidIsbn10(String isbn) {
    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
    }
    char lastChar = isbn.charAt(9);
    sum += (lastChar == 'X') ? 10 : Character.getNumericValue(lastChar);

    return sum % 11 == 0;
  }
}
