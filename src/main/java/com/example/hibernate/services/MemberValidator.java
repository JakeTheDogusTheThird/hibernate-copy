package com.example.hibernate.services;

import com.example.hibernate.models.Member;

public class MemberValidator implements Validator<Member>{
  @Override
  public boolean isValid(Member member) {
    if (member == null) {
      return false;
    }
    return isNonBlank(member.getFirstName())
        && isNonBlank(member.getLastName())
        && isValidEmail(member);
  }

  private boolean isValidEmail(Member member) {
    return member.getEmail() != null
        && member.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
  }
}
