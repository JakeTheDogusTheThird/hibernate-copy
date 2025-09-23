package com.example.hibernate.services;

import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.MemberDao;

import java.util.List;
import java.util.Set;

public class MemberService {
  private final MemberDao memberDao;

  public MemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public void addMember(Member member) {
    this.memberDao.save(member);
  }

  public Member findMember(int id) {
    return this.memberDao.findById(id);
  }

  public Member updateMember(Member member) {
    return this.memberDao.update(member);
  }

  public void deleteMember(Member member) {
    this.memberDao.delete(member);
  }

  public List<Member> listMembers() {
    return this.memberDao.findAll();
  }

  public Member findMemberByEmail(String email) {
    return this.memberDao.findByEmail(email);
  }

  public Set<Loan> showMemberLoans(int id) {
    return this.findMember(id).getLoans();
  }
}
