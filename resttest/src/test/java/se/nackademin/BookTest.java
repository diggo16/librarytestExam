package se.nackademin;

import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.resttest.model.*;

public class BookTest extends BaseTest {

    
    public BookTest() {
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
    @Test
    public void testFetchBook() {
       int id = 1;
       Response response = bookOperations.getBookResponse(id);
       assertEquals("Status code should be 200", 200, response.statusCode());
    }
    
    @Test
    public void testDeleteBook() {
        int id = new Random().nextInt(1000) + 500;
        
        SingleBook singleBook = bookOperations.createRandomBook(id, authorOperations.getAuthor(1));
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response deleteResponse = bookOperations.deleteBook(id);
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
        
        Response getResponse = bookOperations.getBookResponse(id);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
    }
        /**
     * Tests for "/books/byauthor/{author_id}"
     */
    @Test
    public void testGetBooksByAuthor() {
        int authorId = 4;
        Author author = authorOperations.getAuthor(authorId);
        
        int bookId1 = new Random().nextInt(1000) + 500;
        SingleBook singleBook1 = bookOperations.createRandomBook(bookId1, author);
        
        Response postBook1Response = bookOperations.postBook(singleBook1);
        assertEquals("Status code should be 201", 201, postBook1Response.statusCode());
        
        int bookId2 = new Random().nextInt(1000) + 500;
        SingleBook singleBook2 = bookOperations.createRandomBook(bookId2, author);
        
        Response postBook2Response = bookOperations.postBook(singleBook2);
        assertEquals("Status code should be 201", 201, postBook2Response.statusCode());
        
        int id = 4;
        Response getResponse = bookOperations.getBooksByAuthorIdResponse(authorId);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Book> books = bookOperations.getBookListFromResponse(getResponse);
        
        for(Book book : books) {
            System.out.println("Book title: " + book.getTitle());
            assertEquals(author.getFirstName(), book.getAuthor().get(0).getFirstName());
            assertEquals(author.getLastName(), book.getAuthor().get(0).getLastName());
        }
        bookOperations.deleteBook(bookId1);
        bookOperations.deleteBook(bookId2);
    }
    
    /**
     * Tests for "/books/{book_id}/authors"
     */
    @Test
    public void testGetAuthorsByBookId () {
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 1; i < 3; i++) {
            expectedAuthors.add(authorOperations.getAuthor(i));
        }
        int id = new Random().nextInt(1000) + 500;
        
        Book book = new Book();
        book.setTitle("title");
        book.setId(id);
        book.setIsbn("isbn");
        book.setDescription("description");
        book.setNbrPages(1);
        book.setPublicationDate("2017-01-01");
        book.setTotalNbrCopies(1);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        String bookPath = "authors";
        List<Author> actualAuthors = bookOperations.getAuthorsFromResponse(getResponse, bookPath);
        
        for(int i = 0; i < actualAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
        }
        
        bookOperations.deleteBook(id);
    }
    
    @Test
    public void testPostAuthorToBook() {
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 1; i < 3; i++) {
            expectedAuthors.add(authorOperations.getAuthor(i));
        }
        int id = new Random().nextInt(1000) + 500;
        
        Book book = new Book();
        book.setTitle("title");
        book.setId(id);
        book.setIsbn("isbn");
        book.setDescription("description");
        book.setNbrPages(1);
        book.setPublicationDate("2017-01-01");
        book.setTotalNbrCopies(1);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Author newAuthor = authorOperations.getAuthor(3);
        expectedAuthors.add(newAuthor);
        SingleAuthor singleAuthor = new SingleAuthor(newAuthor);
        Response postAuthorResponse = authorOperations.postAuthorToBookResponse(id, singleAuthor);
        assertEquals("Status code should be 200", 200, postAuthorResponse.statusCode());
        
        Response getResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        String bookPath = "authors";
        List<Author> actualAuthors = bookOperations.getAuthorsFromResponse(getResponse, bookPath);
        
        for(int i = 0; i < actualAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
        }
        
        bookOperations.deleteBook(id);             
    }
    
    @Test
    public void testPutAuthorOnBook() {
         List<Author> authors = new ArrayList<>();
        for(int i = 1; i < 3; i++) {
            authors.add(authorOperations.getAuthor(i));
        }
        int id = new Random().nextInt(1000) + 500;
        
        Book book = new Book();
        book.setTitle("title");
        book.setId(id);
        book.setIsbn("isbn");
        book.setDescription("description");
        book.setNbrPages(1);
        book.setPublicationDate("2017-01-01");
        book.setTotalNbrCopies(1);
        book.setAuthor(authors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
       
        Response getAnotherBookAuthorsResponse = authorOperations.getAuthorsByBookIdResponse(4);
        assertEquals("Status code should be 200", 200, getAnotherBookAuthorsResponse.statusCode());
        
        String bookPath = "authors";
        List<Author> expectedAuthors = bookOperations.getAuthorsFromResponse(getAnotherBookAuthorsResponse, bookPath);
        
        Response putResponse = authorOperations.putAuthorToBookResponse(id, getAnotherBookAuthorsResponse.body().peek().print());
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Response getNewAuthorsResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getNewAuthorsResponse.statusCode());
        
        
        
        Response getResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> actualAuthors = bookOperations.getAuthorsFromResponse(getResponse, bookPath);
        
        for(int i = 0; i < actualAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
        }
        
        bookOperations.deleteBook(id);
        
    }
    
}
