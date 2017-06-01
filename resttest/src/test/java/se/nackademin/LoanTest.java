/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import se.nackademin.resttest.AuthorOperations;
import se.nackademin.resttest.BookOperations;
import se.nackademin.resttest.LoanOperations;
import se.nackademin.resttest.UserOperations;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.Loan;
import se.nackademin.resttest.model.User;
import se.nackademin.resttest.model.single.SingleAuthor;
import se.nackademin.resttest.model.single.SingleBook;

/**
 *
 * @author daniel
 */
public class LoanTest extends BaseTest {
    
    public LoanTest() {
        
    }

    @Test
    public void testGetLoans() {
        Response getResponse = LoanOperations.getLoansResponse();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Loan> loanList = LoanOperations.getLoansFromResponse(getResponse);
        
        loanList.forEach((loan)->{
            assertNotNull(loan);
        });
    }

    @Test
    public void testPostLoan() {
        Loan loan = LoanOperations.createAndGetRandomLoan();
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = LoanOperations.getLoansResponse();
        List<Loan> loanList = LoanOperations.getLoansFromResponse(getResponse);
        Loan actualLoan = loanList.get(loanList.size() - 1);
        
        assertEquals(loan.getBook().getId(), actualLoan.getBook().getId());
        assertEquals(loan.getUser().getId(), actualLoan.getUser().getId());
        
        LoanOperations.cleanRandomLoan();
        LoanOperations.deleteLoanResponse(actualLoan);
        
    }
    @Test
    public void testPostLoanWithNonExistingUser() {
        User user = UserOperations.createRandomUser();
        int bookId = new Random().nextInt(1000) + 500;
        Book book = BookOperations.createRandomBook(bookId);
        int authorId = new Random().nextInt(10000) + 1000;
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        AuthorOperations.postAuthorResponse(singleAuthor);
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        book.setAuthor(authorList);
        SingleBook singleBook = new SingleBook(book);
        BookOperations.postBook(singleBook);
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        int loanId = new Random().nextInt(1000) + 500;
        loan.setId(loanId);
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 400", 400, postResponse.statusCode());
        
        Response getResponse = LoanOperations.getLoanResponse(loanId);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
        
    }

    @Test
    public void getLoanById() {
        Loan loan = LoanOperations.createAndGetRandomLoan();
        int id = new Random().nextInt(1000) + 1000;
        loan.setId(id);
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = LoanOperations.getLoanResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        Loan actualLoan = getResponse.jsonPath().getObject("loan", Loan.class);
        
        assertEquals(loan.getBook().getId(), actualLoan.getBook().getId());
        assertEquals(loan.getUser().getId(), actualLoan.getUser().getId());
        
        LoanOperations.cleanRandomLoan();
        LoanOperations.deleteLoanResponse(loan);
        
    }
    @Test
    public void getLoanByInvalidId() {
        Response getResponse = LoanOperations.getLoanResponse(-1);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
    }

    @Test
    public void deleteLoan() {
        Loan loan = LoanOperations.createAndGetRandomLoan();
        int id = new Random().nextInt(1000) + 1000;
        loan.setId(id);
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response deleteResponse = LoanOperations.deleteLoanResponse(loan);
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
        
        Response getResponse = LoanOperations.getLoanResponse(id);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
        LoanOperations.cleanRandomLoan();
        
    }
    @Test
    public void deleteLoanWithInvalidID() {
        Loan loan = new Loan();
        loan.setId(-1);
        Response deleteResponse = LoanOperations.deleteLoanResponse(loan);
        assertEquals("Status code should be 404", 404, deleteResponse.statusCode());
        
    }

    /**
     * tests for /loans/ofuser/{user_id}
     */
    @Test
    public void getLoansByUserId() {
        Loan loan = LoanOperations.createAndGetRandomLoan();
        int id = new Random().nextInt(1000) + 1000;
        loan.setId(id);
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = LoanOperations.getLoansOfUserResponse(loan.getUser().getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Loan> userLoans = LoanOperations.getLoansFromResponse(getResponse);
        
        assertEquals("Size should be 1", 1, userLoans.size());
        Loan userLoan = userLoans.get(0);
        assertEquals(loan.getId(), userLoan.getId());
        assertEquals(loan.getBook().getId(), userLoan.getBook().getId());
        assertEquals(loan.getDateBorrowed(), userLoan.getDateBorrowed());
        assertEquals(loan.getDateDue(), userLoan.getDateDue());
        
        LoanOperations.deleteLoanResponse(loan);
        LoanOperations.cleanRandomLoan();
        
    }
    @Test
    public void getLoansByInvalidUserId() {
        Response getResponse = LoanOperations.getLoansOfUserResponse(-1);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
        
        List<Loan> userLoans = LoanOperations.getLoansFromResponse(getResponse);
        assertEquals("Size should be 0", 0, userLoans.size());
    }

    /**
     * tests of /loans/ofbook/{book_id}
     */
    @Test
    public void getLoansByBookId() {
        Loan loan = LoanOperations.createAndGetRandomLoan();
        int id = new Random().nextInt(1000) + 1000;
        loan.setId(id);
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = LoanOperations.getLoansOfBookResponse(loan.getBook().getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Loan> bookLoans = LoanOperations.getLoansFromResponse(getResponse);
        
        assertEquals("Size should be 1", 1, bookLoans.size());
        Loan bookLoan = bookLoans.get(0);
        assertEquals(loan.getId(), bookLoan.getId());
        assertEquals(loan.getUser().getId(), bookLoan.getUser().getId());
        assertEquals(loan.getDateBorrowed(), bookLoan.getDateBorrowed());
        assertEquals(loan.getDateDue(), bookLoan.getDateDue());
        
        LoanOperations.deleteLoanResponse(loan);
        LoanOperations.cleanRandomLoan();
        
    }
    @Test
    public void getLoansByInvalidBookId() {
        Response getResponse = LoanOperations.getLoansOfBookResponse(-1);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
        
        List<Loan> bookLoans = LoanOperations.getLoansFromResponse(getResponse);
        
        assertEquals("Size should be 0", 0, bookLoans.size());
        
    }

    /**
     * tests of /loans/ofuser/{user_id}/ofbook/{book_id}
     */
        @Test
        public void getLoansByUserAndBookId() {
            Loan loan = LoanOperations.createAndGetRandomLoan();
            int id = new Random().nextInt(1000) + 1000;
            loan.setId(id);
            Response postResponse = LoanOperations.postLoanResponse(loan);
            assertEquals("Status code should be 201", 201, postResponse.statusCode());

            Response getResponse = LoanOperations.getLoansOfUserAndBookResponse(loan.getUser().getId(), loan.getBook().getId());
            assertEquals("Status code should be 200", 200, getResponse.statusCode());

            Loan userAndBookLoan = LoanOperations.getLoanFromResponse(getResponse);

            assertEquals(loan.getId(), userAndBookLoan.getId());
            assertEquals(loan.getDateBorrowed(), userAndBookLoan.getDateBorrowed());
            assertEquals(loan.getDateDue(), userAndBookLoan.getDateDue());

            LoanOperations.deleteLoanResponse(loan);
            LoanOperations.cleanRandomLoan();

        }
        @Test
        public void getLoansByInvalidUserAndBookId() {
            Response getResponse = LoanOperations.getLoansOfUserAndBookResponse(-1, -1);
            assertEquals("Status code should be 404", 404, getResponse.statusCode());

            Loan userAndBookLoan = LoanOperations.getLoanFromResponse(getResponse);
            assertNull(userAndBookLoan);
        }
}
