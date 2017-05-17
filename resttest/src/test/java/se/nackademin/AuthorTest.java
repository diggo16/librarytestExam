/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.SingleAuthor;

/**
 *
 * @author daniel
 */
public class AuthorTest extends BaseTest {
    
    public AuthorTest() {
    }
    
    @Test
    public void testGetAuthor() {   // TODO finnish test
        Author author = authorOperations.getAuthor(1);
        
        
    }
    @Ignore
    @Test
    public void testPostAuthor() {  // TODO check that the author is created with a GET
        int id = new Random().nextInt(1000) + 500;
        Author author = new Author();
        author.setFirstName("Hakan");
        author.setLastName("Brakan");
        author.setBio("hello bio");
        author.setCountry("Sweden");
        author.setId(id);
        String path = "http://localhost:8080/librarytest-rest/authors";
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).post(path);
        assertEquals("Status code should be 201", 201, response.statusCode());
        
        Response authorResponse = authorOperations.getAuthorResponse(id);
        assertEquals("Status code should be 200", 200, authorResponse.statusCode());
    }
    
}
