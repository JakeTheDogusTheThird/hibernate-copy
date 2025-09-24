package com.example.hibernate.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class HibernateGenericDao<T, ID extends Serializable> implements GenericDao<T, ID> {

  private final SessionFactory sessionFactory;
  private final Class<T> persistentClass;

  public HibernateGenericDao(SessionFactory sessionFactory, Class<T> persistentClass) {
    this.sessionFactory = sessionFactory;
    this.persistentClass = persistentClass;
  }

  protected Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public T findById(ID id) {
    return executeInTransaction(session -> session.find(persistentClass, id));
  }

  @Override
  public List<T> findAll() {
    return executeInTransaction(session -> session.createQuery(
        "from " + persistentClass.getSimpleName(),
        persistentClass
    ).list());
  }

  @Override
  public T save(T entity) {
    return executeInTransaction(session -> {
      session.persist(entity);
      return entity;
    });
  }

  @Override
  public T update(T entity) {
    return executeInTransaction(session -> session.merge(entity));
  }

  @Override
  public void delete(T entity) {
    executeInTransaction(session -> {
      session.remove(entity);
      return null;
    });
  }

  @Override
  public void deleteById(ID id) {
    T entity = findById(id);
    if (entity != null) {
      delete(entity);
    }
  }

  protected <R> R executeInTransaction(Function<Session, R> action) {
    Transaction transaction = null;
    R result = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      result = action.apply(session);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw e;
    }
    return result;
  }
}