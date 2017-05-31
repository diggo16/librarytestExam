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
    @Ignore
    @Test   // ERROR TODO FIX
    public void testPutLoan() {

        User user = UserOperations.getUserFromResponse(UserOperations.getUserResponse(12));
        int bookId = new Random().nextInt(1000)+ 1000;
        Book book = BookOperations.createRandomBook(bookId);
        
        int authorId = new Random().nextInt(10000) + 1000;
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        book.setAuthor(authorList);
        SingleBook singleBook = new SingleBook(book);
        Response postBookResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postBookResponse.statusCode());
        int id = new Random().nextInt(1000)+1000;
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setId(id);
        String dateBorrowed = "2017-04-22";
        String dateDue = "2017-06-04";
        loan.setDateBorrowed(dateBorrowed);
        loan.setDateDue(dateDue);
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        System.out.println(loan);
        dateBorrowed = "2017-05-22";
        dateDue = "2017-07-04";
        loan.setDateBorrowed(dateBorrowed);
        loan.setDateDue(dateDue);
        
        loan = LoanOperations.getLoansFromResponse(LoanOperations.getLoanResponse(id)).get(0);
        
        System.out.println(loan);
        Response putResponse = LoanOperations.putLoanResponse(loan);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Response getNewLoanResponse = LoanOperations.getLoanResponse(loan);
        
        Loan actualLoan = LoanOperations.getLoansFromResponse(getNewLoanResponse).get(0);
        
        assertEquals(loan.getBook().getId(), actualLoan.getBook().getId());
        assertEquals(loan.getUser().getId(), actualLoan.getUser().getId());
        
        //LoanOperations.deleteLoanResponse(actualLoan);
        //BookOperations.deleteBook(book.getId());
        //LoanOperations.cleanRandomLoan();*/
        
        
        
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

            Response getResponse = LoanOperations.getLoansOfBookResponse(loan.getBook().getId());
            assertEquals("Status code should be 200", 200, getResponse.statusCode());

            List<Loan> userAndBookLoans = LoanOperations.getLoansFromResponse(getResponse);

            assertEquals("Size should be 1", 1, userAndBookLoans.size());
            Loan userAndBookLoan = userAndBookLoans.get(0);
            assertEquals(loan.getId(), userAndBookLoan.getId());
            assertEquals(loan.getDateBorrowed(), userAndBookLoan.getDateBorrowed());
            assertEquals(loan.getDateDue(), userAndBookLoan.getDateDue());

            LoanOperations.deleteLoanResponse(loan);
            LoanOperations.cleanRandomLoan();

        }
}
