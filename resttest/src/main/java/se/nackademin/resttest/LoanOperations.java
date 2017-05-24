/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.Loan;
import se.nackademin.resttest.model.User;
import se.nackademin.resttest.model.single.SingleLoan;

/**
 *
 * @author daniel
 */
public class LoanOperations extends BaseOperations {
    
    private static final Logger LOG = Logger.getLogger(UserOperations.class.getName());
    
    public static Response getLoansResponse() {
        LOG.log(Level.INFO, "GET all loans Response");
        String resourceName = "loans";
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;  
    }
    
    public static Response postLoanResponse(Loan loan) {
        LOG.log(Level.INFO, "POST loan {0}", loan.toString());
        String resourceName = "loans";
        SingleLoan singleLoan = new SingleLoan(loan);
        Response postResponse = given().contentType(ContentType.JSON).body(singleLoan).post(BASE_URL + resourceName);
        return postResponse;
    }
    
    public static List<Loan> getLoansFromResponse(Response response) {
        LOG.log(Level.INFO, "GET loan map list from response");
        List<Loan> loanList = new ArrayList<>();
        JsonPath jsonPath = response.jsonPath();
        String path = "loans.loan";
        
        if(jsonPath.getInt("loans.size()") == 0) {
            return loanList;
        }
        String loanClassName = jsonPath.getString("loans.loan.getClass().getSimpleName()");

        if(loanClassName.equals("HashMap")) {
            loanList.add(new Loan(jsonPath.getMap(path)));
        }
       
        if(loanClassName.equals("List")) {
            List<Map> mapList = jsonPath.getList(path);
            loanList = getLoanListFromMapList(mapList);
        }
        
        return loanList;
    }

    private static List<Loan> getLoanListFromMapList(List<Map> mapList) {
        LOG.log(Level.INFO, "GET loan list from map list");
        List<Loan> loanList = new ArrayList<>();
        
        mapList.forEach((loanMap)->{
           Loan loan = new Loan(loanMap);
           loanList.add(loan);
        });
        return loanList;
    }
    
}
