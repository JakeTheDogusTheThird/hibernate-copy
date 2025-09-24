package com.example.hibernate.repositories;

import com.example.hibernate.models.Book;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class BookDao extends HibernateGenericDao<Book, Integer> {
  public BookDao(SessionFactory sessionFactory) {
    super(sessionFactory, Book.class);
  }

  public List<Book> searchByKeyword(String keyword) {
    return executeInTransaction(session -> {
          Query<Book> query = session.createNativeQuery("""
              SELECT *
                FROM books
               WHERE title LIKE %?%
              """, Book.class);
          query.setParameter(1, keyword);
          return query.getResultList();
        }
    );
  }

  public List<Book> findAvailableCopiesByIsbn(String isbn) {
    return executeInTransaction(session -> {
      Query<Book> query = session.createNativeQuery("""
          SELECT *
            FROM books AS b
           WHERE b.isbn = ?
             AND b.id not in (SELECT l.book_id
                                FROM loans AS l
                               WHERE l.return_date IS NULL)
          """, Book.class);
      query.setParameter(1, isbn);
      return query.getResultList();
    });
  }
}
