/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import com.jayway.restassured.response.Response;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.resttest.LoanOperations;
import se.nackademin.resttest.model.Loan;

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
    
}
