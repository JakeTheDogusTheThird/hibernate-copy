package com.example.hibernate;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.BookDao;
import com.example.hibernate.repositories.LoanDao;
import com.example.hibernate.repositories.MemberDao;
import com.example.hibernate.services.BookService;
import com.example.hibernate.services.LoanService;
import com.example.hibernate.services.MemberService;
import com.example.hibernate.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
  public static void main(String[] args) {
    HibernateUtil util = new HibernateUtil();
    SessionFactory factory = util.buildSessionFactory();

    BookDao bookDao = new BookDao(factory);
    BookService bookService = new BookService(bookDao);

    MemberDao memberDao = new MemberDao(factory);
    MemberService memberService = new MemberService(memberDao);

    LoanDao loanDao = new LoanDao(factory);
    LoanService loanService = new LoanService(loanDao, bookDao, memberDao);

    Book book = new Book(
        "100",
        "Harry Potter",
        "Joan Rowling",
        "2000"
    );

    book = bookService.addBook(book);
    System.out.println(book);


    Member john = new Member("John", "Doe", "johnDoe@mail.com");
    Member alice = new Member("Alice", "Doe", "johnDoe@mail.com");
    memberService.addMember(john);
    memberService.addMember(alice);
    System.out.println(john);
    System.out.println(alice);

    Loan loan = loanService.loanBook(book.getIsbn(), john.getEmail());
    System.out.println(loan);

  }
}
