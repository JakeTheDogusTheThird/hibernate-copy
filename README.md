import java.util.List;
import java.util.Optional;

public interface BookService {
    Book addBook(Book book);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
    void removeBook(int id);
}

import java.util.Optional;
import java.util.List;

public interface MemberService {
    Member registerMember(Member member);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
    void removeMember(int id);
}
import java.time.LocalDate;
import java.util.List;

public interface LoanService {
    Loan borrowBook(Book book, Member member, LocalDate loanDate);
    Loan returnBook(int loanId, LocalDate returnDate);
    List<Loan> findLoansByMember(Member member);
    List<Loan> findActiveLoans();
}


public class MemberValidator implements Validator<Member> {

    @Override
    public boolean isValid(Member member) {
        if (member == null) {
            return false;
        }

        return member.getFirstName() != null && !member.getFirstName().isBlank()
            && member.getLastName() != null && !member.getLastName().isBlank()
            && member.getEmail() != null && member.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}


import java.time.LocalDate;

public class LoanValidator implements Validator<Loan> {

    @Override
    public boolean isValid(Loan loan) {
        if (loan == null) {
            return false;
        }

        LocalDate loanDate = loan.getLoanDate();
        LocalDate returnDate = loan.getReturnDate();

        return loan.getBook() != null
            && loan.getMember() != null
            && loanDate != null
            && returnDate != null
            && !returnDate.isBefore(loanDate);
    }
}
public class BookValidator implements Validator<Book> {

    @Override
    public boolean isValid(Book book) {
        if (book == null) {
            return false;
        }

        return isValidIsbn(book.getIsbn())
            && book.getTitle() != null && !book.getTitle().isBlank()
            && book.getAuthor() != null && !book.getAuthor().isBlank()
            && book.getYear() != null && book.getYear().matches("\\d{4}");
    }

    private boolean isValidIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }

        String cleanIsbn = isbn.replace("-", "").trim();

        if (cleanIsbn.matches("\\d{13}")) {
            return isValidIsbn13(cleanIsbn);
        } else if (cleanIsbn.matches("\\d{9}[\\dX]")) {
            return isValidIsbn10(cleanIsbn);
        }
        return false;
    }

    private boolean isValidIsbn13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }

    private boolean isValidIsbn10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (Character.getNumericValue(isbn.charAt(i)) * (10 - i));
        }
        char lastChar = isbn.charAt(9);
        sum += (lastChar == 'X') ? 10 : Character.getNumericValue(lastChar);
        return sum % 11 == 0;
    }
}
