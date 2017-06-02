package se.nackademin;

import se.nackademin.resttest.model.single.SingleBook;
import se.nackademin.resttest.model.single.SingleAuthor;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.resttest.AuthorOperations;
import se.nackademin.resttest.BookOperations;
import se.nackademin.resttest.model.*;

public class BookTest {

    
    public BookTest() {
    }
    
    /**
     * Tests for "/books"
     */

    @Test
    public void testFetchBooks() {
        Response response = BookOperations.getAllBooksResponse();
        assertEquals("Status code should be 200", 200, response.statusCode());
        
        List<Book> bookList = BookOperations.getBookListFromHashMap(response.jsonPath().getList("books.book"));
        bookList.forEach((book)->{
            assertNotNull(book);
        });
    }

    @Test
    public void testPostBook() {
        
        int authorId = BookOperations.getRandomId();
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = BookOperations.getRandomId();     
        Book postBook = BookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Book book = BookOperations.getBook(id);

        assertEquals(singleBook.getBook().getTitle(), book.getTitle());
        assertEquals(singleBook.getBook().getDescription(), book.getDescription());
        assertEquals(singleBook.getBook().getIsbn(), book.getIsbn());
        assertEquals(singleBook.getBook().getNbrPages(), book.getNbrPages());
        assertEquals(singleBook.getBook().getPublicationDate(), book.getPublicationDate());
        assertEquals(singleBook.getBook().getTotalNbrCopies(), book.getTotalNbrCopies());
        List<Author> authorList = book.getAuthorList();
        assertEquals(author.getFirstName(), authorList.get(0).getFirstName());
        
        BookOperations.deleteBook(id);
        AuthorOperations.deleteAuthor(authorId);

    }

    @Test
    public void testPostBookWithTakenId() {
        
        int authorId = BookOperations.getRandomId();
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = 4;        
        Book postBook = BookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 400", 400, postResponse.statusCode());
        
        AuthorOperations.deleteAuthor(authorId);

    }

    @Test
    public void testPutBook() {
        int authorId = BookOperations.getRandomId();
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = BookOperations.getRandomId();      
        Book postBook = BookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        String newTitle = "hell title";
        postBook.setTitle(newTitle);
        SingleBook putSingleBook = new SingleBook(postBook);
        Response putResponse = BookOperations.putBook(putSingleBook);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Book getBook = BookOperations.getBook(id);
        assertEquals(postBook.getTitle(), getBook.getTitle());
        
        BookOperations.deleteBook(id);
        AuthorOperations.deleteAuthor(authorId);
    }

    @Test
    public void testPutBookWithNoTitle() {
        
        int id = 6;
        Book oldBook = BookOperations.getBook(id);
        Book putBook = new Book();
        putBook.setId(oldBook.getId());
        putBook.setDescription(oldBook.getDescription());
        putBook.setIsbn(oldBook.getIsbn());
        
        SingleBook putSingleBook = new SingleBook(putBook);
        Response putResponse = BookOperations.putBook(putSingleBook);
        assertEquals("Status code should be 400", 400, putResponse.statusCode());

    }
    
    /**
     * Tests for "/books/{id}"
     */

    @Test
    public void testFetchBook() {
        int authorId = BookOperations.getRandomId();
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        int id = new Random().nextInt(1000) + 500;        
        Book postBook = BookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response response = BookOperations.getBookResponse(id);
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
        
        BookOperations.deleteBook(id);
        AuthorOperations.deleteAuthor(authorId);
       
    }

    @Test
    public void testFetchBookWithInvalidId() {
        Response response = BookOperations.getBookResponse(-1);
        assertEquals("Status code should be 404", 404, response.statusCode());
    }
    
    @Test
    public void testDeleteBook() {
        int id = BookOperations.getRandomId();
        
        List<Author> authors = new ArrayList<>();
        authors.add(AuthorOperations.getAuthor(1));
               
        Book postBook = BookOperations.createRandomBook(id);
        postBook.setAuthor(authors);
        SingleBook singleBook = new SingleBook(postBook);
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response deleteResponse = BookOperations.deleteBook(id);
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
        
        Response getResponse = BookOperations.getBookResponse(id);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
    }

    @Test
    public void testDeleteBookWithInvalidId() {
        Response deleteResponse = BookOperations.deleteBook(-1);
        assertEquals("Status code should be 404", 404, deleteResponse.statusCode());
    }
    
    /**
     * Tests for "/books/byauthor/{author_id}"
     */
    @Test
    public void testGetBooksByAuthor() {
        
        int authorId = AuthorOperations.getRandomId();
        Author author = AuthorOperations.createRandomAuthor(authorId);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        
        List<Integer> idList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();
        
        for(int i = 0; i < 2; i++) {
            int id = BookOperations.getRandomId();
            idList.add(id);
            Book book = BookOperations.createRandomBook(id);
            book.setAuthor(authors);
            bookList.add(book);
            
            SingleBook singleBook = new SingleBook(book);
            Response postBookResponse = BookOperations.postBook(singleBook);
            assertEquals("Status code should be 201", 201, postBookResponse.statusCode());
        }
        
        Response getResponse = BookOperations.getBooksByAuthorIdResponse(authorId);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Book> authorBookList = BookOperations.getBookListFromResponse(getResponse);
        
        for(int i = 0; i < authorBookList.size(); i++) {
            List<Author> actualAuthorList = bookList.get(i).getAuthor();
            assertEquals(actualAuthorList.get(0).getId(), author.getId());
            assertEquals(actualAuthorList.get(0).getFirstName(), author.getFirstName());
            assertEquals(actualAuthorList.get(0).getCountry(), author.getCountry());
            assertEquals(actualAuthorList.get(0).getBio(), author.getBio());
        }
        
        idList.forEach((id)->{
            BookOperations.deleteBook(id);
        });
        AuthorOperations.deleteAuthor(authorId);
    }
    
    /**
     * Tests for "/books/{book_id}/authors"
     */
    @Test
    public void testGetAuthorsByBookId () {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            int authorId = AuthorOperations.getRandomId();
            Author author = AuthorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }
        
        int id = BookOperations.getRandomId();
        Book book = BookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = AuthorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> actualAuthors = AuthorOperations.getAuthorListFromResponse(getResponse, "authors");
        
        for(int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
            assertEquals(expectedAuthors.get(i).getBio(), actualAuthors.get(i).getBio());
            assertEquals(expectedAuthors.get(i).getCountry(), actualAuthors.get(i).getCountry());
        }
        
        BookOperations.deleteBook(id);
        expectedAuthors.forEach((author)->{
            AuthorOperations.deleteAuthor(author.getId());
        });
    }

    @Test
    public void testGetAuthorsByBookInvalidId () {

        Response getResponse = AuthorOperations.getAuthorsByBookIdResponse(-1);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());

    }
    
    @Test
    public void testPostAuthorToBook() {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            int authorId = BookOperations.getRandomId();
            Author author = AuthorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }
        
        int id = BookOperations.getRandomId();
        Book book = BookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        int newAuthorId = BookOperations.getRandomId();
        Author newAuthor = AuthorOperations.createRandomAuthor(newAuthorId);
        expectedAuthors.add(newAuthor);
        
        SingleAuthor singleAuthor = new SingleAuthor(newAuthor);
        Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
        assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
        Response postAuthorToBookResponse = AuthorOperations.postAuthorToBookResponse(id, singleAuthor);
        assertEquals("Status code should be 200", 200, postAuthorToBookResponse.statusCode());
        
        Response getResponse = AuthorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<Author> actualAuthors = AuthorOperations.getAuthorListFromResponse(getResponse, "authors");
        
        for(int i = 0; i < expectedAuthors.size(); i++) {
            assertEquals(expectedAuthors.get(i).getId(), actualAuthors.get(i).getId());
            assertEquals(expectedAuthors.get(i).getFirstName(), actualAuthors.get(i).getFirstName());
            assertEquals(expectedAuthors.get(i).getLastName(), actualAuthors.get(i).getLastName());
            assertEquals(expectedAuthors.get(i).getBio(), actualAuthors.get(i).getBio());
            assertEquals(expectedAuthors.get(i).getCountry(), actualAuthors.get(i).getCountry());
        }
        
        BookOperations.deleteBook(id);
        expectedAuthors.forEach((author)->{
            AuthorOperations.deleteAuthor(author.getId());
        });
    }

    @Test
    public void testPostAuthorThatAlreadyExistToBook() {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            int authorId = AuthorOperations.getRandomId();
            Author author = AuthorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }
        
        int id = BookOperations.getRandomId();
        Book book = BookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        SingleAuthor singleAuthor = new SingleAuthor(expectedAuthors.get(0));
        Response postAuthorToBookResponse = AuthorOperations.postAuthorToBookResponse(id, singleAuthor);
        assertEquals("Status code should be 400", 400, postAuthorToBookResponse.statusCode());
        
        BookOperations.deleteBook(id);
        expectedAuthors.forEach((author)->{
            AuthorOperations.deleteAuthor(author.getId());
        });
    }

    @Test
    public void testPutAuthorOnBook() {
        
        List<Author> authorList = new ArrayList<>();
        List<Integer> authorsIdList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            int authorId = AuthorOperations.getRandomId();
            authorsIdList.add(authorId);
            Author author = AuthorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            authorList.add(author);
        }
        
        int id = BookOperations.getRandomId();
        Book book = BookOperations.createRandomBook(id);
        book.setAuthor(authorList);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getAnotherBookAuthorsResponse = AuthorOperations.getAuthorsByBookIdResponse(4);
        assertEquals("Status code should be 200", 200, getAnotherBookAuthorsResponse.statusCode());
        
        String bookPath = "authors";
        List<Author> expectedAuthors = BookOperations.getAuthorsFromResponse(getAnotherBookAuthorsResponse, bookPath);
        
        Response putResponse = AuthorOperations.putAuthorToBookResponse(id, getAnotherBookAuthorsResponse.body().peek().print());
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Response getAuthorsResponse = AuthorOperations.getAuthorsByBookIdResponse(id);
        assertEquals("Status code should be 200", 200, getAuthorsResponse.statusCode());
        List<Author> actualAuthors = AuthorOperations.getAuthorListFromResponse(getAuthorsResponse, "authors");
        
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
        BookOperations.deleteBook(id);   
        authorsIdList.forEach((authorId)->{
            AuthorOperations.deleteAuthor(authorId);
        });
    }

    @Test
    public void testPutEmptyAuthorsOnBook() {
        
        List<Author> expectedAuthors = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            int authorId = AuthorOperations.getRandomId();
            Author author = AuthorOperations.createRandomAuthor(authorId);
            SingleAuthor singleAuthor = new SingleAuthor(author);
            Response postAuthorResponse = AuthorOperations.postAuthorResponse(singleAuthor);
            assertEquals("Status code should be 201", 201, postAuthorResponse.statusCode());
            expectedAuthors.add(author);
        }      
        
        int id = BookOperations.getRandomId();
        Book book = BookOperations.createRandomBook(id);
        book.setAuthor(expectedAuthors);
        SingleBook singleBook = new SingleBook(book);
        Response postResponse = BookOperations.postBook(singleBook);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        
        Response getBookAuthorsResponse = AuthorOperations.getAuthorsByBookIdResponse(4);
        assertEquals("Status code should be 200", 200, getBookAuthorsResponse.statusCode());
        
        Response putResponse = AuthorOperations.putAuthorToBookResponse(id, "");
        assertEquals("Status code should be 400", 400, putResponse.statusCode());
        
        BookOperations.deleteBook(id);
        expectedAuthors.forEach((author)->{
            AuthorOperations.deleteAuthor(author.getId());
        });
    }   
}
