package com.example.hibernate.repositories;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class LoanDao extends HibernateGenericDao<Loan, Integer> {
  public LoanDao(SessionFactory sessionFactory) {
    super(sessionFactory, Loan.class);
  }

  public List<Book> findBooksOnLoan() {
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      Query<Book> query = session.createNativeQuery(
          """
              SELECT l.books_id
                FROM loans AS l
               WHERE l.return_date IS null
              """,
          Book.class);
      List<Book> booksOnLoan = query.getResultList();
      transaction.commit();
      return booksOnLoan;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }
}
