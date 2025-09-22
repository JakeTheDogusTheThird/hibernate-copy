BookService
addBook(Book)
updateBook(Book) ? -> DAO
deleteBook(Book) ? -> DAO
findBookByIsbn(isbn) when creating a loan -> DAO
searchBooks(keyword)
listAllBooks()

MemberService
registerMember()
updateMember()
deleteMember()
findByEmail(email) email is natural id
listAll()
getMemberLoans(id)

LoanService
loanBook(isbn, member_id)
returnBook(isbn, member_id)
findActiveLoansByMember(member_id)
findOverdueLoans()
listAll()
