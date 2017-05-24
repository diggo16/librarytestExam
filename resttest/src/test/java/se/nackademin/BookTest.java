package se.nackademin;

import se.nackademin.resttest.model.single.SingleBook;
import se.nackademin.resttest.model.single.SingleAuthor;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import se.nackademin.resttest.model.*;

public class BookTest extends BaseTest {

    
    public BookTest() {
    }
    
    /**
     * Tests for "/books"
     */
    //@Ignore
    @Test
    public void testFetchBooks() {
        Response response = bookOperations.getAllBooksResponse();
        assertEquals("Status code should be 200", 200, response.statusCode());
        
        List<Book> bookList = bookOperations.getBookListFromHashMap(response.jsonPath().getList("books.book"));
        bookList.forEach((book)->{
            assertNotNull(book);
        });
    }
    //@Ignore
    @Test
    public void testPostBook() {
        
        int authorId = new Random().nextInt(1000) + 500;
        Author author = authorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = new Random().nextInt(1000) + 500;        
        Book postBook = bookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Book book = bookOperations.getBook(id);

        assertEquals(singleBook.getBook().getTitle(), book.getTitle());
        assertEquals(singleBook.getBook().getDescription(), book.getDescription());
        assertEquals(singleBook.getBook().getIsbn(), book.getIsbn());
        assertEquals(singleBook.getBook().getNbrPages(), book.getNbrPages());
        assertEquals(singleBook.getBook().getPublicationDate(), book.getPublicationDate());
        assertEquals(singleBook.getBook().getTotalNbrCopies(), book.getTotalNbrCopies());
        List<Author> authorList = book.getAuthorList();
        assertEquals(author.getFirstName(), authorList.get(0).getFirstName());
        
        bookOperations.deleteBook(id);
        authorOperations.deleteAuthor(authorId);

    }
    //@Ignore
    @Test
    public void testPutBook() {
        int authorId = new Random().nextInt(1000) + 500;
        Author author = authorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = new Random().nextInt(1000) + 500;        
        Book postBook = bookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        String newTitle = "hell title";
        postBook.setTitle(newTitle);
        SingleBook putSingleBook = new SingleBook(postBook);
        Response putResponse = bookOperations.putBook(putSingleBook);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Book getBook = bookOperations.getBook(id);
        assertEquals(postBook.getTitle(), getBook.getTitle());
        
        bookOperations.deleteBook(id);
    }
    
    /**
     * Tests for "/books/{id}"
     */
    //@Ignore
    @Test
    public void testFetchBook() {
        int authorId = new Random().nextInt(1000) + 500;
        Author author = authorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = new Random().nextInt(1000) + 500;        
        Book postBook = bookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response response = bookOperations.getBookResponse(id);
        assertEquals("Status code should be 200", 200, response.statusCode());
       
        Book book = response.jsonPath().getObject("book", Book.class);
        
        assertEquals(postBook.getTitle(), book.getTitle());
        assertEquals(postBook.getDescription(), book.getDescription());
        assertEquals(postBook.getIsbn(), book.getIsbn());
        assertEquals(postBook.getNbrPages(), book.getNbrPages());
        assertEquals(postBook.getPublicationDate(), book.getPublicationDate());
        assertEquals(postBook.getTotalNbrCopies(), book.getTotalNbrCopies());
        List<Author> authorList = book.getAuthorList();
        assertEquals(author.getFirstName(), authorList.get(0).getFirstName());
        
        bookOperations.deleteBook(id);
        authorOperations.deleteAuthor(authorId);
       
    }
    
    //@Ignore
    @Test
    public void testDeleteBook() {
        int id = new Random().nextInt(1000) + 500;
        
        List<Author> authors = new ArrayList<>();
        authors.add(authorOperations.getAuthor(1));
               
        Book postBook = bookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
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
    //@Ignore
    @Test
    public void testGetBooksByAuthor() {
        
        int authorId = new Random().nextInt(1000) + 500;
        Author author = authorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        List<Integer> idList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();
        
        for(int i = 0; i < 2; i++) {
            int id = new Random().nextInt(1000) + 500;
            idList.add(id);
            Book book = bookOperations.createRandomBook(id);
            book.setAuthor(authors);
            bookList.add(book);
            
            SingleBook singleBook = new SingleBook(book);
            Response postBookResponse = bookOperations.postBook(singleBook);
            assertEquals("Status code should be 201", 201, postBookResponse.statusCode());
        }
        
        Response getResponse = bookOperations.getBooksByAuthorIdResponse(authorId);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Book> authorBookList = bookOperations.getBookListFromResponse(getResponse);
        
        for(int i = 0; i < authorBookList.size(); i++) {
            List<Author> actualAuthorList = bookList.get(i).getAuthor();
            assertEquals(actualAuthorList.get(0).getId(), author.getId());
            assertEquals(actualAuthorList.get(0).getFirstName(), author.getFirstName());
            assertEquals(actualAuthorList.get(0).getCountry(), author.getCountry());
            assertEquals(actualAuthorList.get(0).getBio(), author.getBio());
        }
        
        idList.forEach((id)->{
            bookOperations.deleteBook(id);
        });
        authorOperations.deleteAuthor(authorId);
    }
    
    /**
     * Tests for "/books/{book_id}/authors"
     */
    //@Ignore
    @Test
    public void testGetAuthorsByBookId () {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            int authorId = new Random().nextInt(1000) + 500;
            Author author = authorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }
        
        int id = new Random().nextInt(1000) + 500;
        Book book = bookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> actualAuthors = authorOperations.getAuthorListFromResponse(getResponse, "authors");
        
        for(int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
            assertEquals(expectedAuthors.get(i).getBio(), actualAuthors.get(i).getBio());
            assertEquals(expectedAuthors.get(i).getCountry(), actualAuthors.get(i).getCountry());
        }
        
        bookOperations.deleteBook(id);
        expectedAuthors.forEach((author)->{
            authorOperations.deleteAuthor(author.getId());
        });
    }
    
    //@Ignore
    @Test
    public void testPostAuthorToBook() {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            int authorId = new Random().nextInt(1000) + 500;
            Author author = authorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }
        
        int id = new Random().nextInt(1000) + 500;
        Book book = bookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        int newAuthorId = new Random().nextInt(1000) + 500;
        Author newAuthor = authorOperations.createRandomAuthor(newAuthorId);
        expectedAuthors.add(newAuthor);
        
        SingleAuthor singleAuthor = new SingleAuthor(newAuthor);
        Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        Response postAuthorToBookResponse = authorOperations.postAuthorToBookResponse(id, singleAuthor);
        assertEquals("Status code should be 200", 200, postAuthorToBookResponse.statusCode());
        
        Response getResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> actualAuthors = authorOperations.getAuthorListFromResponse(getResponse, "authors");
        
        for(int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
            assertEquals(expectedAuthors.get(i).getBio(), actualAuthors.get(i).getBio());
            assertEquals(expectedAuthors.get(i).getCountry(), actualAuthors.get(i).getCountry());
        }
        
        bookOperations.deleteBook(id);
        expectedAuthors.forEach((author)->{
            authorOperations.deleteAuthor(author.getId());
        });
    }
    //@Ignore
    @Test
    public void testPutAuthorOnBook() {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            int authorId = new Random().nextInt(1000) + 500;
            Author author = authorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = authorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }
        
        int id = new Random().nextInt(10000) + 500;
        Book book = bookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = bookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        expectedAuthors.forEach((author)->{
            authorOperations.deleteAuthor(author.getId());
        });
        Response getAnotherBookAuthorsResponse = authorOperations.getAuthorsByBookIdResponse(4);
        assertEquals("Status code should be 200", 200, getAnotherBookAuthorsResponse.statusCode());
        
        String bookPath = "authors";
        expectedAuthors = bookOperations.getAuthorsFromResponse(getAnotherBookAuthorsResponse, bookPath);
        
        Response putResponse = authorOperations.putAuthorToBookResponse(id, getAnotherBookAuthorsResponse.body().peek().print());
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Response getAuthorsResponse = authorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getAuthorsResponse.statusCode());
        List<Author> actualAuthors = authorOperations.getAuthorListFromResponse(getAuthorsResponse, "authors");
        
        assertEquals("Author lists should be same size", expectedAuthors.size(), actualAuthors.size());

        expectedAuthors.forEach((expectedAuthor)->{
            
            int index = actualAuthors.indexOf(expectedAuthor);
            if(index >= 0 ) {
                assertEquals(expectedAuthor.getFirstName(), actualAuthors.get(index).getFirstName());
                assertEquals(expectedAuthor.getLastName(), actualAuthors.get(index).getLastName());
                assertEquals(expectedAuthor.getBio(), actualAuthors.get(index).getBio());
                assertEquals(expectedAuthor.getCountry(), actualAuthors.get(index).getCountry());
            }
            else
            {
                fail("expected author didn't exist in the actual author list");
            }
        });
        
        bookOperations.deleteBook(id);
    }
    
}
