package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.single.SingleBook;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookOperations extends BaseOperations {
    
private static final Logger LOG = Logger.getLogger(BookOperations.class.getName());

    public BookOperations() {
    }
    
    public static Response getBookResponse(int id) {
        String resourceName = "books/" + id;
        return getResponse(LOG, resourceName);
    }
    public static Response getBooksByAuthorIdResponse(int id) {
        String resourceName = "books/byauthor/" + Integer.toString(id);
        return getResponse(LOG, resourceName);
    }
    
    public static Response getAllBooksResponse() {
        String resourceName = "books";
        return getResponse(LOG, resourceName);
    }
    
    public static Response postBook(SingleBook singleBook) {
        List<Author> authorList = singleBook.getBook().getAuthorList();
        Object[] bookValues = new Object[]{singleBook.getBook().getId(), singleBook.getBook().getTitle(), authorList.get(0).getFirstName() + 
                " " + authorList.get(0).getLastName(), singleBook.getBook().getDescription(), singleBook.getBook().getIsbn(), 
                singleBook.getBook().getNbrPages()};
        LOG.log(Level.INFO, "POST book Response with the values: id:{0}, title:{1}, author name:{2}, description:{3}, isbn:{4}, pages:{5}", bookValues);
        
        String resourceName = "books";
        Response response = given().contentType(ContentType.JSON).body(singleBook).post(BASE_URL + resourceName);
        return response;
    }
    public static Response putBook(SingleBook singleBook) {
        Object[] bookValues = new Object[]{singleBook.getBook().getId(), singleBook.getBook().getTitle(), singleBook.getBook().getAuthor(),
                singleBook.getBook().getDescription(), singleBook.getBook().getIsbn(), 
                singleBook.getBook().getNbrPages()};
        LOG.log(Level.INFO, "PUT book Response with the values: id:{0}, title:{1}, author name:{2}, description:{3}, isbn:{4}, pages:{5}", bookValues);
        
        String resourceName = "books";
        Response response = given().contentType(ContentType.JSON).body(singleBook).put(BASE_URL + resourceName);
        return response;
    }
    
    public static Response deleteBook(int id) {
        LOG.log(Level.INFO, "DELETE book Response with id {0}", id);
        String deleteResourceName = "books/" + id;
        Response deleteResponse = delete(BASE_URL + deleteResourceName);
        return deleteResponse;
    }
    
    public static Book createRandomBook(int id) {
        LOG.log(Level.INFO, "Create random book with the id {0}", id);
        String description = "desc";
        String title = "title";
        String isbn = "734-326-34";
        String publicationDate = "2017-01-01";
        int totalNbrCopies = 3;
        int nbrPages = 50;
        
        Book book = new Book();
        book.setDescription(description);
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setNbrPages(nbrPages);
        book.setPublicationDate(publicationDate);
        book.setTotalNbrCopies(totalNbrCopies);
        book.setId(id);
        return book;
    }
    public static Book getBook(int id) {
        LOG.log(Level.INFO, "GET book with id {0}", id);
        String resourceName = "books/" + Integer.toString(id);
        Book book = given().accept(ContentType.JSON).get(BASE_URL + resourceName).jsonPath().getObject("book", Book.class);
        return book;
    }
    
    public static List<Book> getBookListFromResponse(Response getResponse) {
        LOG.log(Level.INFO, "get book list from response");
        List<Book> books = new ArrayList<>();
        
        String className = getResponse.jsonPath().getString("books.book.getClass()");
        
        LOG.log(Level.INFO, "class: {0}", className);
        LOG.log(Level.INFO, "book list in json: {0}", getResponse.jsonPath().get("books.book.title").toString());
        
        if(className.equals("class java.util.ArrayList")) {
            List<HashMap> bookHashMapList = getResponse.jsonPath().getList("books.book");
            for(int i = 0; i < bookHashMapList.size(); i++) {
                
                String bookPath = "books.book[" + i + "]";
                List<Author> authors = getAuthorsFromResponse(getResponse, bookPath);
               
                Book book = new Book(bookHashMapList.get(i), authors);
                books.add(book);
            }
        }
        
        if(className.equals("class java.util.HashMap")) {
            Book book = getResponse.jsonPath().getObject("books.book", Book.class);
            
            String bookPath = "books.book";
            List<Author> authors = getAuthorsFromResponse(getResponse, bookPath);

             book.setAuthor(authors);
        }

        return books;
    }
    
    public static List<Author> getAuthorsFromResponse(Response response, String bookPath) {
        LOG.log(Level.INFO, "get author list from the path {0} from response", bookPath);
        String className = response.jsonPath().getString(bookPath + ".author.getClass()");
          String resource = bookPath + ".author";
          LOG.log(Level.INFO, "className: {0}", className);
          LOG.log(Level.INFO, "authors from book in json: {0}", response.jsonPath().get(resource).toString());
            List<Author> authorList = new ArrayList<>();
            
             if(className.equals("class java.util.HashMap")) {
                Author author = response.jsonPath().getObject(resource, Author.class);
                authorList.add(author);
             }
             
             if(className.equals("class java.util.ArrayList")) {
                 resource = bookPath + ".author.size()";
                 int s = response.jsonPath().getInt(resource);
                 for(int k = 0; k < s; k++) {
                     resource = bookPath + ".author[" + k + "]";
                     Author author = response.jsonPath().getObject(resource, Author.class);
                     authorList.add(author);     
                 }
             }
             return authorList;
    }
    
    public static List<Book> getBookListFromHashMap(List<HashMap> bookHashMapList) {
        LOG.log(Level.INFO, "GET book list from hash map");
        List<Book> bookList = new ArrayList<>();
        
        bookHashMapList.forEach((bookHashMap)->{
           Book book = new Book(bookHashMap);
           bookList.add(book);
        });
        return bookList;
    }
    
}
