package com.example.hibernate.repositories;

import com.example.hibernate.models.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Objects;

public class MemberDao extends HibernateGenericDao<Member, Integer> {
  public MemberDao(SessionFactory sessionFactory) {
    super(sessionFactory, Member.class);
  }

  public Member findByEmail(String email) {
    Transaction transaction = null;

    try (Session session = getCurrentSession()) {
      transaction = session.beginTransaction();
      Member member = session.bySimpleNaturalId(Member.class).load(email);
      transaction.commit();
      return member;
    } catch (RuntimeException re) {
      if (Objects.nonNull(transaction)) {
        transaction.rollback();
      }
      throw re;
    }
  }
}
