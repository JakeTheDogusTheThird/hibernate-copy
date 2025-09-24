package com.example.hibernate.repositories;

import com.example.hibernate.models.Member;
import org.hibernate.SessionFactory;

public class MemberDao extends HibernateGenericDao<Member, Integer> {
  public MemberDao(SessionFactory sessionFactory) {
    super(sessionFactory, Member.class);
  }

  public Member findByEmail(String email) {
    return executeInTransaction(session -> session.bySimpleNaturalId(Member.class).load(email));
  }
}
