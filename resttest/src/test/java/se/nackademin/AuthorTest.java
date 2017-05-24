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
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.single.SingleAuthor;

/**
 *
 * @author daniel
 */
public class AuthorTest extends BaseTest {
    
    public AuthorTest() {
    }
    @Test
    public void testGetAuthors() {
        Response getResponse = authorOperations.getAllAuthorsResponse();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> authorList = authorOperations.getAuthorListFromHashMap(getResponse.jsonPath().getList("authors.author"));
        
        authorList.forEach((author)->{
            assertNotNull(author);
        });
    }
    
    @Test
    public void testPostAuthor() {
        int id = new Random().nextInt(1000) + 500;
        Author randomAuthor = authorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        Response postResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = authorOperations.getAuthorResponse(id);
        Author author = getResponse.jsonPath().getObject("author", Author.class);
        
        assertEquals(randomAuthor.getFirstName(), author.getFirstName());
        assertEquals(randomAuthor.getLastName(), author.getLastName());
        assertEquals(randomAuthor.getCountry(), author.getCountry());
        assertEquals(randomAuthor.getBio(), author.getBio());
        
        authorOperations.deleteAuthor(id);
    }
    
    @Test
    public void testPutAuthor() {
        int id = new Random().nextInt(1000) + 500;
        Author randomAuthor = authorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        Response postResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        randomAuthor.setFirstName("first");
        randomAuthor.setLastName("last");
        randomAuthor.setBio("no Bio");
        randomAuthor.setCountry("Finland");
        
        singleAuthor = new SingleAuthor(randomAuthor);
        Response putResponse = authorOperations.putAuthorResponse(singleAuthor);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Author author = authorOperations.getAuthor(id);
        
        assertEquals(randomAuthor.getFirstName(), author.getFirstName());
        assertEquals(randomAuthor.getLastName(), author.getLastName());
        assertEquals(randomAuthor.getCountry(), author.getCountry());
        assertEquals(randomAuthor.getBio(), author.getBio());
        
        authorOperations.deleteAuthor(id);
    }
    
 
    @Test
    public void testGetAuthor() {
        int id = new Random().nextInt(1000) + 500;
        Author randomAuthor = authorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        authorOperations.postAuthorResponse(singleAuthor);
        
        Response getResponse = authorOperations.getAuthorResponse(id);
        Author author = getResponse.jsonPath().getObject("author", Author.class);
        assertNotNull(author);
        
        authorOperations.deleteAuthor(id);
    }
    
    @Test
    public void testDeleteAuthor() {
        int id = new Random().nextInt(1000) + 500;
        Author randomAuthor = authorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        Response postResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response deleteResponse = authorOperations.deleteAuthor(id);
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
        
        Response getResponse = authorOperations.getAuthorResponse(id);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
    }
    
}
