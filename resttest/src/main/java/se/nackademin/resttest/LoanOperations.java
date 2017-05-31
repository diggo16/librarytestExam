/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static se.nackademin.resttest.BaseOperations.BASE_URL;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.Loan;
import se.nackademin.resttest.model.User;
import se.nackademin.resttest.model.single.SingleAuthor;
import se.nackademin.resttest.model.single.SingleBook;
import se.nackademin.resttest.model.single.SingleLoan;

/**
 *
 * @author daniel
 */
public class LoanOperations extends BaseOperations {
    
    private static final Logger LOG = Logger.getLogger(UserOperations.class.getName());
    private static Loan randomLoan;
    
    public static Response getLoansResponse() {
        LOG.log(Level.INFO, "GET all loans Response");
        String resourceName = "loans";
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;  
    }
    
    public static Response getLoansOfUserResponse(int userId) {
        LOG.log(Level.INFO, "GET all loans Response");
        String resourceName = "loans/ofuser/" + userId;
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;  
    }
    
    public static Response getLoansOfBookResponse(int bookId) {
        LOG.log(Level.INFO, "GET all loans Response");
        String resourceName = "loans/ofbook/" + bookId;
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;  
    }
    
    public static Response getLoansOfUserAndBookResponse(int userId, int bookId) {
        LOG.log(Level.INFO, "GET all loans Response");
        String resourceName = "loans/ofuser/" + userId + "/ofbook/" + bookId;
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;  
    }
    
    public static Response getLoanResponse(Loan loan) {
        LOG.log(Level.INFO, "GET loan {0} Response", loan.getId());
        String resourceName = "loans/" + loan.getId();
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;  
    }
    
    public static Response getLoanResponse(int id) {
        LOG.log(Level.INFO, "GET loan {0} Response", id);
        String resourceName = "loans/" + id;
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
    
    public static Response putLoanResponse(Loan loan) {
        LOG.log(Level.INFO, "PUT loan {0} Response", loan.toString());
        String resourceName = "loans";
        SingleLoan singleLoan = new SingleLoan(loan);
        Response putResponse = given().contentType(ContentType.JSON).body(singleLoan).put(BASE_URL + resourceName);
        return putResponse;
    }
    
    public static Response deleteLoanResponse(Loan loan) {
        Integer id = loan.getId();
        String resourceName = "loans/" + id;
        LOG.log(Level.INFO, "delete {0}", resourceName);
        return delete(BASE_URL + resourceName);
        
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
       
        if(loanClassName.equals("ArrayList")) {
            List<Map> mapList = jsonPath.getList(path);
            loanList = getLoanListFromMapList(mapList);
        }
        
        return loanList;
    }
    
    public static Loan createAndGetRandomLoan() {
        User user = UserOperations.createRandomUser();
        UserOperations.postUserResponse(user);
        int bookId = new Random().nextInt(10000) + 1000;
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
        loan.setUser(user);
        loan.setBook(book);
        String dateBorrowed = "2017-01-01";
        String dateDue = "2017-03-03";
        loan.setDateBorrowed(dateBorrowed);
        loan.setDateDue(dateDue);
        randomLoan = loan;
        return loan;
    }
    
    public static void cleanRandomLoan() {
        int userId = randomLoan.getUser().getId();
        int bookId = randomLoan.getBook().getId();
        
        deleteLoanResponse(randomLoan);
        
        UserOperations.deleteUserResponse(userId);
        BookOperations.deleteBook(bookId);
        
        randomLoan = null;
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
