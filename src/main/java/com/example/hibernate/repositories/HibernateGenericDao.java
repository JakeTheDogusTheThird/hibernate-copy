package com.example.hibernate.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      T result = session.find(persistentClass, id);
      transaction.commit();
      return result;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }

  @Override
  public List<T> findAll() {
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      List<T> result = session.createQuery(
          "from " + persistentClass.getSimpleName(),
          persistentClass
      ).list();
      transaction.commit();
      return result;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }

  @Override
  public T save(T entity) {
    Objects.requireNonNull(entity);
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      session.persist(entity);
      transaction.commit();
      return entity;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }

  @Override
  public T update(T entity) {
    Objects.requireNonNull(entity);
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      entity = session.merge(entity);
      transaction.commit();
      return entity;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }

  @Override
  public void delete(T entity) {
    Objects.requireNonNull(entity);
    Transaction transaction = null;
    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      session.remove(entity);
      transaction.commit();
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }

  @Override
  public void deleteById(ID id) {
    T entity = findById(id);
    if (entity != null) {
      delete(entity);
    }
  }
}