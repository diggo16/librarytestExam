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
import se.nackademin.resttest.AuthorOperations;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.single.SingleAuthor;

/**
 *
 * @author daniel
 */
public class AuthorTest {
    
    @Test
    public void testGetAuthors() {
        Response getResponse = AuthorOperations.getAllAuthorsResponse();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> authorList = AuthorOperations.getAuthorListFromHashMap(getResponse.jsonPath().getList("authors.author"));
        
        authorList.forEach((author)->{
            assertNotNull(author);
        });
    }
    
    @Test
    public void testPostAuthor() {
        int id = AuthorOperations.getRandomId();
        Author randomAuthor = AuthorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        Response postResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = AuthorOperations.getAuthorResponse(id);
        Author author = getResponse.jsonPath().getObject("author", Author.class);
        
        assertEquals(randomAuthor.getFirstName(), author.getFirstName());
        assertEquals(randomAuthor.getLastName(), author.getLastName());
        assertEquals(randomAuthor.getCountry(), author.getCountry());
        assertEquals(randomAuthor.getBio(), author.getBio());
        
        AuthorOperations.deleteAuthor(id);
    }
    
    @Test
    public void testPostAuthorThatAlreadyExist() {
        int id = AuthorOperations.getRandomId();
        Author randomAuthor = AuthorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        AuthorOperations.postAuthorResponse(singleAuthor);
        Response postResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 400", 400, postResponse.statusCode());
        
        AuthorOperations.deleteAuthor(id);
    }
    
    @Test
    public void testPutAuthor() {
        int id = AuthorOperations.getRandomId();
        Author randomAuthor = AuthorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        Response postResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        randomAuthor.setFirstName("first");
        randomAuthor.setLastName("last");
        randomAuthor.setBio("no Bio");
        randomAuthor.setCountry("Finland");
        
        singleAuthor = new SingleAuthor(randomAuthor);
        Response putResponse = AuthorOperations.putAuthorResponse(singleAuthor);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Author author = AuthorOperations.getAuthor(id);
        
        assertEquals(randomAuthor.getFirstName(), author.getFirstName());
        assertEquals(randomAuthor.getLastName(), author.getLastName());
        assertEquals(randomAuthor.getCountry(), author.getCountry());
        assertEquals(randomAuthor.getBio(), author.getBio());
        
        AuthorOperations.deleteAuthor(id);
    }
    
    @Test
    public void testPutAuthorWithNoFirstName() {
        int id = AuthorOperations.getRandomId();
        Author randomAuthor = AuthorOperations.createRandomAuthor(id);
        Author oldAuthor = new Author(randomAuthor);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        AuthorOperations.postAuthorResponse(singleAuthor);
        
        randomAuthor.setFirstName(null);
        randomAuthor.setLastName("last");
        randomAuthor.setBio("no Bio");
        randomAuthor.setCountry("Finland");
        
        singleAuthor = new SingleAuthor(randomAuthor);
        Response putResponse = AuthorOperations.putAuthorResponse(singleAuthor);
        assertEquals("Status code should be 400", 400, putResponse.statusCode());
        
        Author author = AuthorOperations.getAuthor(id);
        
        assertEquals(oldAuthor.getFirstName(), author.getFirstName());
        assertEquals(oldAuthor.getLastName(), author.getLastName());
        assertEquals(oldAuthor.getCountry(), author.getCountry());
        assertEquals(oldAuthor.getBio(), author.getBio());
        
        AuthorOperations.deleteAuthor(id);
    }    
 
    @Test
    public void testGetAuthor() {
        int id = AuthorOperations.getRandomId();
        Author randomAuthor = AuthorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        AuthorOperations.postAuthorResponse(singleAuthor);
        
        Response getResponse = AuthorOperations.getAuthorResponse(id);
        assertEquals("Status should be 200", 200, getResponse.statusCode());
        Author author = getResponse.jsonPath().getObject("author", Author.class);
        assertNotNull(author);
        
        AuthorOperations.deleteAuthor(id);
    }
    
    @Test
    public void testGetAuthorWithInvalidId() {
        Response getResponse = AuthorOperations.getAuthorResponse(-1);
        assertEquals("Status should be 404", 404, getResponse.statusCode());
    }
    
    @Test
    public void testDeleteAuthor() {
        int id = AuthorOperations.getRandomId();
        Author randomAuthor = AuthorOperations.createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(randomAuthor);
        AuthorOperations.postAuthorResponse(singleAuthor);
        
        Response deleteResponse = AuthorOperations.deleteAuthor(id);
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
        
        Response getResponse = AuthorOperations.getAuthorResponse(id);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
    }
    
    @Test
    public void testDeleteAuthorWithInvalidId() {
        Response deleteResponse = AuthorOperations.deleteAuthor(-1);
        assertEquals("Status code should be 404", 404, deleteResponse.statusCode());
    }
    
}
