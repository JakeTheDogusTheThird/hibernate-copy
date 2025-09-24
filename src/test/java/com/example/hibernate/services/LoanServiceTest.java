package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.BookDao;
import com.example.hibernate.repositories.LoanDao;
import com.example.hibernate.repositories.MemberDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoanServiceTest {
  @Mock
  private BookDao bookDao;
  @Mock
  private MemberDao memberDao;
  @Mock
  private LoanDao loanDao;

  private BookService bookService;
  private MemberService memberService;
  private LoanService loanService;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    BookValidator bookValidator = new BookValidator();
    MemberValidator memberValidator = new MemberValidator();
    LoanValidator loanValidator = new LoanValidator();

    bookService = new BookService(bookDao, bookValidator);
    memberService = new MemberService(memberDao, memberValidator);
    loanService = new LoanService(loanDao, loanValidator, bookService, memberService);
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  void calculatePopularityIndex() {
  }

  @Test
  void loanBook() {
    Book book1 = new Book("0-306-40615-2", "Title", "Author", "2022");
    Book book2 = new Book("0-306-40615-2", "Title", "Author", "2022");
    Member member = new Member("John", "Doe", "john@mail.com");
    book1.setId(1);
    book2.setId(2);

    when(bookDao.findAvailableCopiesByIsbn(book1.getIsbn()))
        .thenReturn(List.of(book1, book2));
    when(memberDao.findByEmail(member.getEmail())).thenReturn(member);
    when(loanDao.findAll()).thenReturn(Collections.emptyList());

    Loan expectedLoan = new Loan(
        book1,
        member,
        LocalDate.of(2025, 9, 24),
        LocalDate.of(2025, 9, 24).plusDays(14));
    when(loanDao.save(expectedLoan)).thenReturn(expectedLoan);
    Loan loan = loanService.loanBook(
        book1.getIsbn(),
        member.getEmail(),
        LocalDate.of(2025, 9, 24));
    assertEquals(expectedLoan, loan);
  }


  @Test
  void returnBook() {
  }

  @Test
  void extendLoan() {
  }

  @Test
  void listAll() {
  }

  @Test
  void listBooksOnLoan() {
  }

  @Test
  void getMemberLoanHistory() {
  }

  @Test
  void getMemberActiveLoans() {
  }

  @Test
  void getBookLoanHistory() {
  }

  @Test
  void getOverdueLoans() {
  }
}