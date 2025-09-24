package com.example.hibernate.models;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

  @Test
  void equals_ShouldReturnTrueForMembersWithSameEmail() {
    Member member1 = new Member();
    Member member2 = new Member();
    member1.setEmail("john@mail.com");
    member2.setEmail("john@mail.com");
    boolean result = member1.equals(member2);
    assertTrue(result);
  }

  @Test
  void equals_ShouldReturnFalseForMembersWithDifferentEmail() {
    Member member1 = new Member();
    Member member2 = new Member();
    member1.setEmail("john@mail.com");
    member2.setEmail("john1@mail.com");
    boolean result = member1.equals(member2);
    assertFalse(result);
  }

  @Test
  void equals_ShouldReturnFalseForNonMemberInstance() {
    Member member1 = new Member();
    Object member2 = new Object();
    boolean result = member1.equals(member2);
    assertFalse(result);
  }

  @Test
  void hashCode_ShouldReturnHashCode() {
    Member member1 = new Member();
    Member member2 = new Member();
    member1.setEmail("john@mail.com");
    member2.setEmail("john@mail.com");
    assertEquals(member1.hashCode(), member2.hashCode());
  }
}