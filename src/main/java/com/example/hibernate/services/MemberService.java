package com.example.hibernate.services;

import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.MemberDao;

import java.util.List;
import java.util.Set;

public class MemberService {
  private final MemberDao memberDao;
  private final MemberValidator memberValidator;

  public MemberService(MemberDao memberDao, MemberValidator memberValidator) {
    this.memberDao = memberDao;
    this.memberValidator = memberValidator;
  }

  public Member createMember(Member member) {
    if (!memberValidator.isValid(member)) {
      return null;
    }
    return this.memberDao.save(member);
  }

  public Member getMemberById(int id) {
    return this.memberDao.findById(id);
  }

  public Member updateMember(Member member) {
    if (!memberValidator.isValid(member)) {
      return null;
    }
    return this.memberDao.update(member);
  }

  public void deleteMember(int id) {
    this.memberDao.deleteById(id);
  }

  public List<Member> listMembers() {
    return this.memberDao.findAll();
  }

  public Member findMemberByEmail(String email) {
    return this.memberDao.findByEmail(email);
  }
}
