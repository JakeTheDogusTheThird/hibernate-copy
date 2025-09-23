package com.example.hibernate.repositories;

import com.example.hibernate.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class BookDao extends HibernateGenericDao<Book, Integer> {
  public BookDao(SessionFactory sessionFactory) {
    super(sessionFactory, Book.class);
  }

  public List<Book> searchByKeyword(String keyword) {
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();

      Query<Book> query = session.createNativeQuery("""
          SELECT *
            FROM books
           WHERE title LIKE %?%
          """, Book.class);
      query.setParameter(1, keyword);
      List<Book> result = query.getResultList();

      transaction.commit();
      return result;

    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }

  public List<Book> findAvailableCopiesByIsbn(String isbn) {
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();

      Query<Book> query = session.createNativeQuery("""
          SELECT *
            FROM books AS b
           WHERE b.isbn = ?
             AND b.id not in (SELECT l.book_id
                                FROM loans AS l
                               WHERE l.return_date IS NULL)
          """,
          Book.class);
      query.setParameter(1, isbn);
      List<Book> result = query.getResultList();
      transaction.commit();
      return result;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }
}
