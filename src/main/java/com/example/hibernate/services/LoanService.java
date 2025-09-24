package com.example.hibernate.services;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.LoanDao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LoanService {
  private static final int MAX_LOAN_PERIOD = 14;
  private static final int MAX_EXTEND_PERIOD = MAX_LOAN_PERIOD / 2;
  private final LoanDao loanDao;
  private final BookService bookService;
  private final MemberService memberService;
  private final LoanValidator loanValidator;

  public LoanService(
      LoanDao loanDao,
      LoanValidator loanValidator,
      BookService bookService,
      MemberService memberService
  ) {
    this.bookService = bookService;
    this.memberService = memberService;
    this.loanDao = loanDao;
    this.loanValidator = loanValidator;
  }

  public double calculatePopularityIndex(String isbn) {
    List<Loan> loans = listAll();
    long borrowedTime = loans
        .stream()
        .filter(loan -> loan.getBook().getIsbn().equals(isbn))
        .count();
    return (double) borrowedTime / loans.size() * 100;
  }

  public Loan loanBook(String isbn, String email, LocalDate loanDate) {
    Member member = memberService.findMemberByEmail(email);
    validateMemberLoans(member);

    Book book = findAvailableBook(isbn);

    Loan loan = new Loan(book, member, loanDate, loanDate.plusDays(MAX_LOAN_PERIOD));
    if (!loanValidator.isValid(loan)) {
      return null;
    }

    return loanDao.save(loan);
  }

  private void validateMemberLoans(Member member) {
    List<Loan> overdueLoans = getOverdueLoans()
        .stream()
        .filter(l -> l.getMember().equals(member))
        .toList();
    if (!overdueLoans.isEmpty()) {
      throw new OverdueLoanException(member, overdueLoans);
    }
  }

  private Book findAvailableBook(String isbn) {
    List<Book> available = bookService.getBookCopies(isbn);
    if (available.isEmpty()) {
      throw new NoAvailableCopiesException(isbn);
    }
    return available.getFirst();
  }

  public void returnBook(int id) {
    Loan loan = this.loanDao.findById(id);
    loan.setReturnDate(LocalDate.now());
    this.loanDao.save(loan);
  }

  public Loan extendLoan(int loanId, LocalDate newReturnDate) {
    Loan loan = loanDao.findById(loanId);
    if (loan == null) throw new NullPointerException("No loan exists with id " + loanId);

    if (newReturnDate.isBefore(loan.getReturnDate())) {
      throw new IllegalArgumentException("Extension is chronologically invalid");
    }

    if (newReturnDate.isAfter(loan.getReturnDate().plusDays(MAX_EXTEND_PERIOD))) {
      throw new IllegalArgumentException("Extension exceeds max allowed period");
    }

    loan.setReturnDate(newReturnDate);
    return this.loanDao.update(loan);
  }

  public List<Loan> listAll() {
    return this.loanDao.findAll();
  }

  public List<Book> listBooksOnLoan() {
    return this.loanDao.findBooksOnLoan();
  }

  public List<Loan> getMemberLoanHistory(Member member) {
    return listAll()
        .stream()
        .filter(loan -> loan.getMember().equals(member))
        .toList();
  }

  public List<Loan> getMemberActiveLoans(Member member) {
    return listAll()
        .stream()
        .filter(loan -> loan.getMember().equals(member))
        .filter(loan -> loan.getReturnDate() == null)
        .toList();
  }

  public List<Loan> getBookLoanHistory(Book book) {
    return listAll()
        .stream()
        .filter(loan -> loan.getBook().equals(book))
        .toList();
  }

  public List<Loan> getOverdueLoans() {
    LocalDate today = LocalDate.now();
    return listAll()
        .stream()
        .filter(loan -> loan.getReturnDate() == null
            && loan.getLoanDate().plusDays(MAX_LOAN_PERIOD).isBefore(today))
        .collect(Collectors.toList());
  }

}
