package com.example.hibernate;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.BookDao;
import com.example.hibernate.repositories.LoanDao;
import com.example.hibernate.repositories.MemberDao;
import com.example.hibernate.services.BookService;
import com.example.hibernate.services.BookValidator;
import com.example.hibernate.services.LoanService;
import com.example.hibernate.services.LoanValidator;
import com.example.hibernate.services.MemberService;
import com.example.hibernate.services.MemberValidator;
import com.example.hibernate.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
  public static void main(String[] args) {
    HibernateUtil util = new HibernateUtil();
    SessionFactory factory = util.buildSessionFactory();

    BookDao bookDao = new BookDao(factory);
    BookValidator bookValidator = new BookValidator();
    BookService bookService = new BookService(bookDao, bookValidator);

    MemberDao memberDao = new MemberDao(factory);
    MemberValidator memberValidator = new MemberValidator();
    MemberService memberService = new MemberService(memberDao, memberValidator);

    LoanDao loanDao = new LoanDao(factory);
    LoanValidator loanValidator = new LoanValidator();
    LoanService loanService = new LoanService(loanDao, loanValidator, bookService, memberService);

    Book book = new Book("0-306-40615-2", "Title1", "Author", "2022");

    book = bookService.createBook(book);
    System.out.println(book);


    Member john = new Member("John", "Doe", "johnDoe@mail.com");
    Member alice = new Member("Alice", "Doe", "aliceDoe@mail.com");
    memberService.createMember(john);
    memberService.createMember(alice);
    System.out.println(john);
    System.out.println(alice);

    Loan loan = loanService.loanBook(book.getIsbn(), john.getEmail());
    System.out.println(loan);

  }
}
