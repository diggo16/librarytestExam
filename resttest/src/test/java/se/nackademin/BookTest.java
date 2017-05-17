package se.nackademin;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import se.nackademin.resttest.*;
import se.nackademin.resttest.model.*;

public class BookTest extends BaseTest {
    
    //private final BookOperations bookOperations;
    //private final AuthorOperations authorOperations;
    
    public BookTest() {
        //bookOperations = new BookOperations();
        //authorOperations = new AuthorOperations();
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
        Response authorResponse = authorOperations.getAuthorResponse(1);
        assertEquals("Status code should be 200", 200, authorResponse.statusCode());
        Author author = authorOperations.getAuthor(1);       
        int id = new Random().nextInt(1000) + 500;        
        
        SingleBook singleBook = bookOperations.createRandomBook(id, author);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Book book = bookOperations.getBook(id);

        assertEquals(singleBook.getBook().getTitle(), book.getTitle());
        assertEquals(singleBook.getBook().getDescription(), book.getDescription());
        assertEquals(singleBook.getBook().getIsbn(), book.getIsbn());
        assertEquals(singleBook.getBook().getNbrPages(), book.getNbrPages());
        assertEquals(singleBook.getBook().getPublicationDate(), book.getPublicationDate());
        assertEquals(singleBook.getBook().getTotalNbrCopies(), book.getTotalNbrCopies());

    }
    @Test
    public void testPutBook() {
        Response authorResponse = authorOperations.getAuthorResponse(1);
        assertEquals("Status code should be 200", 200, authorResponse.statusCode());
        Author author = authorOperations.getAuthor(1);       
        int id = new Random().nextInt(1000) + 500;        
        
        SingleBook postSingleBook = bookOperations.createRandomBook(id, author);
        Response postResponse = bookOperations.postBook(postSingleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        // Change values on the book
        Book book = bookOperations.getBook(id);
        String randomTitle = UUID.randomUUID().toString().substring(0, 12);
        book.setTitle(randomTitle);
        SingleBook putSingleBook = new SingleBook(book);
        
        Response response = bookOperations.putBook(putSingleBook);
        assertEquals("Status code should be 200", 200, response.statusCode());
        
        Book getBook = bookOperations.getBook(id);
        assertEquals(book.getTitle(), getBook.getTitle());
    }
    
    /**
     * Tests for "/books/{id}"
     */
    @Ignore
    @Test
    public void testFetchBook() {
        fail();
    }
    
    @Ignore
    @Test
    public void testDeleteBook() {
        fail();
    }
    
    /**
     * Tests for "/books/{book_id}/authors"
     */
    @Ignore
    @Test
    public void testGetBookByAuthor() {
        fail();
    }
    
    /**
     * Tests for "/books/byauthor/{author_id}"
     */
    @Ignore
    @Test
    public void testGetAuthorsByBookId() {
        fail();
    }
    
    @Ignore
    @Test
    public void testPostAuthorToBook() {
        fail();
    }
    
    @Ignore
    @Test
    public void testPutAuthorOnBook() {
        fail();
    }
    
}
