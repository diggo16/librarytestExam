/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import com.jayway.restassured.response.Response;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.resttest.BookOperations;
import se.nackademin.resttest.LoanOperations;
import se.nackademin.resttest.UserOperations;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.Loan;
import se.nackademin.resttest.model.User;
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
    
    @Test   // ERROR TODO FIX
    public void testPutLoan() {
        Loan loan = LoanOperations.createAndGetRandomLoan();
        Response postResponse = LoanOperations.postLoanResponse(loan);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        String dateBorrowed = "2017-01-01";
        String dateDue = "2017-03-03";
        loan.setDateBorrowed(dateBorrowed);
        loan.setDateDue(dateDue);
        //loan.setId(null);
        System.out.println(loan);
        Response putResponse = LoanOperations.putLoanResponse(loan);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Response getNewLoanResponse = LoanOperations.getLoanResponse(loan);
        
        Loan actualLoan = LoanOperations.getLoansFromResponse(getNewLoanResponse).get(0);
        
        assertEquals(loan.getBook().getId(), actualLoan.getBook().getId());
        assertEquals(loan.getUser().getId(), actualLoan.getUser().getId());
        
        LoanOperations.deleteLoanResponse(actualLoan);
        
        
        
    }
    
}
