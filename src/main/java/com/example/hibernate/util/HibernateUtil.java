package com.example.hibernate.util;

import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

  private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();

  public SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      return new MetadataSources(registry)
          .addAnnotatedClass(Book.class)
          .addAnnotatedClass(Member.class)
          .addAnnotatedClass(Loan.class)
          .buildMetadata()
          .buildSessionFactory();
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed

      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

}
