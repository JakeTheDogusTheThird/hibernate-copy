package com.example.hibernate.repositories;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class LoanDao extends HibernateGenericDao<Loan, Integer> {
  public LoanDao(SessionFactory sessionFactory) {
    super(sessionFactory, Loan.class);
  }

  public List<Book> findBooksOnLoan() {
    return executeInTransaction(session -> {
      Query<Book> query = session.createNativeQuery(
          """
              SELECT l.books_id
                FROM loans AS l
               WHERE l.return_date IS null
              """,
          Book.class);
      return query.getResultList();
    });
  }
}
