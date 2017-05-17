package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.AuthorsMap;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.SingleBook;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookOperations extends BaseOperations {
    
private static final Logger LOG = Logger.getLogger(BookOperations.class.getName());
    
    public Response getBookResponse(int id) {
        LOG.log(Level.INFO, "GET book Response with id {0}", id);
        String resourceName = "books/" + id;
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    public Response getBookByAuthorIdResponse(int id) {
        LOG.log(Level.INFO, "GET books Response by the author with the id {0}", id);
        String resourceName = "books/byauthor/" + Integer.toString(id);
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response getAllBooksResponse() {
        LOG.log(Level.INFO, "GET all books Response");
        String resourceName = "books";
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response getAuthorsFromBookResponse(int id) {
        LOG.log(Level.INFO, "GET authors Response from the book with id {0}", id);
        String resourceName = "books/" + id + "/authors";
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response postBook(SingleBook singleBook) {
        Object[] bookValues = new Object[]{singleBook.getBook().getId(), singleBook.getBook().getTitle(), singleBook.getBook().getAuthor().getFirstName() + 
                " " + singleBook.getBook().getAuthor().getLastName(), singleBook.getBook().getDescription(), singleBook.getBook().getIsbn(), 
                singleBook.getBook().getNbrPages()};
        LOG.log(Level.INFO, "POST book Response with the values: id:{0}, title:{1}, author name:{2}, description:{3}, isbn:{4}, pages:{5}", bookValues);
        String resourceName = "books";
        Response response = given().contentType(ContentType.JSON).body(singleBook).post(BASE_URL + resourceName);
        return response;
    }
    public Response putBook(SingleBook singleBook) {
        Object[] bookValues = new Object[]{singleBook.getBook().getId(), singleBook.getBook().getTitle(), singleBook.getBook().getAuthor(),
                singleBook.getBook().getDescription(), singleBook.getBook().getIsbn(), 
                singleBook.getBook().getNbrPages()};
        LOG.log(Level.INFO, "PUT book Response with the values: id:{0}, title:{1}, author name:{2}, description:{3}, isbn:{4}, pages:{5}", bookValues);
        String resourceName = "books";
        Response response = given().contentType(ContentType.JSON).body(singleBook).put(BASE_URL + resourceName);
        return response;
    }
    
    public Response deleteBook(int id) {
        LOG.log(Level.INFO, "DELETE book Response with id {0}", id);
        String deleteResourceName = "books/" + id;
        Response deleteResponse = delete(BASE_URL + deleteResourceName);
        return deleteResponse;
    }
    
    public SingleBook createRandomBook(int id, Author author) {
        LOG.log(Level.INFO, "Create random book with the id {0} and author {1}", new Object[]{id, author.getFirstName() + " " + author.getLastName()});
        String description = "desc";
        String title = "title";
        String isbn = "73432634";
        String publicationDate = "2017-01-01";
        int totalNbrCopies = 1;
        int nbrPages = 50;
        
        Book book = new Book();
        book.setDescription(description);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setNbrPages(nbrPages);
        book.setPublicationDate(publicationDate);
        book.setTotalNbrCopies(totalNbrCopies);
        book.setId(id);
        book.setAuthor(author);
        SingleBook singleBook = new SingleBook(book);
        return singleBook;
    }
    public Book getBook(int id) {
        LOG.log(Level.INFO, "GET book with id {0}", id);
        String resourceName = "books/" + Integer.toString(id);
        Book book = given().accept(ContentType.JSON).get(BASE_URL + resourceName).jsonPath().getObject("book", Book.class);
        return book;
    }
    public Book setRandomValuesToBook(Book book) {
        LOG.log(Level.INFO, "Set random values to book with id {0}", book.getId());
        String description = UUID.randomUUID().toString();
        String title = UUID.randomUUID().toString();
        String isbn = UUID.randomUUID().toString();
        int nbrPages = 100;
        
        book.setDescription(description);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setNbrPages(nbrPages);
        return book;
    }
    
    public Response putAuthorsToBook(int id, AuthorsMap authors) {
        LOG.log(Level.INFO, "PUT authors to book with id {0}", id);
        String resourceName = "books/" + id + "/authors";
        Response response = given().contentType(ContentType.JSON).body(authors).put(BASE_URL + resourceName);
        return response;
    }
    
}
