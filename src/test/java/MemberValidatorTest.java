import com.example.hibernate.models.Member;
import com.example.hibernate.services.MemberValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MemberValidatorTest {
  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    MemberValidator validator = new MemberValidator();
    Member member = new Member("john", "doe", "john@mail.com");
    boolean result = validator.isValid(member);
    assertTrue(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullMember(Member member) {
    MemberValidator validator = new MemberValidator();
    boolean result = validator.isValid(member);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void isValid_ShouldReturnFalseForNullOrEmptyFirstName(String firstName) {
    MemberValidator validator = new MemberValidator();
    Member member = new Member(firstName, "doe", "john@mail.com");
    boolean result = validator.isValid(member);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void isValid_ShouldReturnFalseForNullOrEmptyLastName(String lastName) {
    MemberValidator validator = new MemberValidator();
    Member member = new Member("john", lastName, "john@mail.com");
    boolean result = validator.isValid(member);
    assertFalse(result);
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullEmail(String email) {
    MemberValidator validator = new MemberValidator();
    Member member = new Member("john", "doe", email);
    boolean result = validator.isValid(member);
    assertFalse(result);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "asdasd!~222.com"})
  void isValid_ShouldReturnFalseForInvalidEmail(String email) {
    MemberValidator validator = new MemberValidator();
    Member member = new Member("john", "doe", email);
    boolean result = validator.isValid(member);
    assertFalse(result);
  }
}
