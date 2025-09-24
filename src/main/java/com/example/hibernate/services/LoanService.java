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

  public Loan loanBook(String isbn, String email) {
    Member member = this.memberService.findMemberByEmail(email);
    List<Loan> overdueLoans = getOverdueLoans()
        .stream()
        .filter(l -> l.getMember().equals(member))
        .toList();
    if (!overdueLoans.isEmpty()) {
      throw new IllegalStateException("Overdue loans exist");
    }

    List<Book> available = this.bookService.getBookCopies(isbn);
    if (available.isEmpty()) {
      throw new IllegalStateException("No available copies of this book.");
    }
    Book book = available.getFirst();
    Loan loan = new Loan(book, member, LocalDate.now(), null);
    if (!loanValidator.isValid(loan)) {
      return null;
    }
    return this.loanDao.save(loan);
  }

  public void returnBook(int id) {
    Loan loan = this.loanDao.findById(id);
    loan.setReturnDate(LocalDate.now());
    this.loanDao.save(loan);
  }

  public Loan extendLoan(int loanId, LocalDate newReturnDate){
    Loan loan = loanDao.findById(loanId);
    if (loan == null) throw new NullPointerException("No loan exists with id " + loanId);

    if (newReturnDate.isAfter(loan.getReturnDate().plusDays(MAX_EXTEND_PERIOD))) {
      throw new IllegalArgumentException("Extension exceeds max allowed period");
    }

    if (newReturnDate.isBefore(loan.getReturnDate())) {
      throw new IllegalArgumentException("Extension is chronologically invalid");
    }

    loan.setReturnDate(newReturnDate);
    return loanDao.update(loan);
  }

  public List<Loan> listAll() {
    return this.loanDao.findAll();
  }

  public List<Book> listBooksOnLoan() {
    return this.loanDao.findBooksOnLoan();
  }

  List<Loan> getOverdueLoans() {
    LocalDate today = LocalDate.now();
    return loanDao.findAll()
        .stream()
        .filter(loan -> loan.getReturnDate() == null
            && loan.getLoanDate().plusDays(MAX_LOAN_PERIOD).isBefore(today))
        .collect(Collectors.toList());
  }

}
