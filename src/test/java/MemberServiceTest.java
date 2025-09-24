import com.example.hibernate.models.Book;
import com.example.hibernate.models.Loan;
import com.example.hibernate.models.Member;
import com.example.hibernate.repositories.MemberDao;
import com.example.hibernate.services.MemberService;
import com.example.hibernate.services.MemberValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

  @Test
  void getMemberLoans_returnsMemberLoans() {
    Book book =  new Book("0-306-40615-2", "Title1", "Author", "2022");
    Member member = new Member("John", "Doe", "john@gmail.com");
    member.setId(1);
    Loan loan1 = new Loan(
        book,
        member,
        LocalDate.of(2025, 9, 24),
        LocalDate.of(2025, 9, 30)
    );
    Loan loan2 = new Loan(
        book,
        member,
        LocalDate.of(2025, 9, 15),
        LocalDate.of(2025, 9, 24)
    );
    loan1.setId(1);
    loan2.setId(2);
    Set<Loan> expectedLoans = Set.of(
        loan1,
        loan2
    );
    member.setLoans(expectedLoans);
    when(memberDao.findById(member.getId())).thenReturn(member);
    Set<Loan> memberLoans = memberService.getMemberLoans(member.getId());
    assertEquals(expectedLoans, memberLoans);
  }
}
