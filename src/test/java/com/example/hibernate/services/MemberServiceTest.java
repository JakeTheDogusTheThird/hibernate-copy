package com.example.hibernate.services;

import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.MemberDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MemberServiceTest {
  @Mock
  private MemberDao memberDao;

  private MemberService memberService;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    MemberValidator memberValidator = new MemberValidator();
    memberService = new MemberService(memberDao, memberValidator);
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  void getMember_returnsMemberById() {
    Member member = new Member("John", "Doe", "john@mail.com");
    int id = 1;
    member.setId(id);
    when(memberDao.findById(id)).thenReturn(member);

    Member foundMember = memberService.getMemberById(id);
    assertEquals(member, foundMember);
  }

  @Test
  void createMember_ValidMember_returnsMember() {
    Member member = new Member("John", "Doe", "john@mail.com");
    when(memberDao.save(member)).thenReturn(member);
    Member addedMember = memberService.createMember(member);
    assertEquals(member, addedMember);
  }

  @Test
  void createMember_InvalidMember_returnsMember() {
    Member member = new Member("John", null, "john@mail.com");
    when(memberDao.save(member)).thenReturn(member);
    Member addedMember = memberService.createMember(member);
    assertNull(addedMember);
  }

  @Test
  void updateMember_ValidMember_returnsMember() {
    Member member = new Member("John", "Doe", "john@mail.com");
    when(memberDao.update(member)).thenReturn(member);
    Member updatedMember = memberService.updateMember(member);
    assertEquals(member, updatedMember);
  }

  @Test
  void updateMember_InvalidMember_returnsMember() {
    Member member = new Member("John", "", "john@mail.com");
    when(memberDao.update(member)).thenReturn(member);
    Member updatedMember = memberService.updateMember(member);
    assertNull(updatedMember);
  }

  @Test
  void deleteMember_verifyDeleteById() {
    int id = 1;
    memberService.deleteMember(id);
    verify(memberDao).deleteById(id);
  }

  @Test
  void listMembers_returnsAllMembers() {
    List<Member> expectedMembers = List.of(
        new Member("John", "Doe", "john@mail.com"),
        new Member("Alice", "Deloitte", "alice@mail.com")
    );
    when(memberDao.findAll()).thenReturn(expectedMembers);
    List<Member> members = memberService.listMembers();
    assertEquals(members, expectedMembers);
  }

  @Test
  void getMemberByEmail_returnsMemberById() {
    Member expectedMember = new Member("John", "Doe", "john@mail.com");
    when(memberDao.findByEmail(expectedMember.getEmail())).thenReturn(expectedMember);
    Member member = memberService.findMemberByEmail(expectedMember.getEmail());
    assertEquals(expectedMember, member);
  }
}
