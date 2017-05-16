package se.nackademin;

import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.resttest.*;

public class BookTest {
    
    private final BookOperations bookOperations;
    private final AuthorOperations authorOperations;
    
    public BookTest() {
        bookOperations = new BookOperations();
        authorOperations = new AuthorOperations();
    }
    
    /**
     * Tests for "/books"
     */
    @Test
    public void testFetchBooks() {
        Response response = bookOperations.getAllBooksResponse();
        assertEquals("Status code should be 200", 200, response.statusCode());
    }
    
    @Test
    public void testPostBook() {
        fail();
    }
    
    @Test
    public void testPutBook() {
        fail();
    }
    
    /**
     * Tests for "/books/{id}"
     */
    @Test
    public void testFetchBook() {
        fail();
    }
    
    @Test
    public void testDeleteBook() {
        fail();
    }
    
    /**
     * Tests for "/books/{book_id}/authors"
     */
    @Test
    public void testGetBookByAuthor() {
        fail();
    }
    
    /**
     * Tests for "/books/byauthor/{author_id}"
     */
    @Test
    public void testGetAuthorsByBookId() {
        fail();
    }
    
    @Test
    public void testPostAuthorToBook() {
        fail();
    }
    
    @Test
    public void testPutAuthorOnBook() {
        fail();
    }
    
}
