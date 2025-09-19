package com.example.hibernate;

import com.example.hibernate.models.Book;
import com.example.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
  public static void main(String[] args) {
    HibernateUtil util = new HibernateUtil();
    SessionFactory factory = util.buildSessionFactory();

    Session session = factory.openSession();
    session.beginTransaction();
    Book book = new Book();

    book.setIsbn("100");
    book.setTitle("Harry Potter");
    book.setAuthor("Joan Rowling");
    book.setYear("200");

    session.persist(book);
    session.getTransaction().commit();
    session.close();
  }
}
