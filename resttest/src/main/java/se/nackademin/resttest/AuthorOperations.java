package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.SingleAuthor;

public class AuthorOperations extends BaseOperations {
    
    private static final Logger LOG = Logger.getLogger(AuthorOperations.class.getName());
    
    public AuthorOperations() {
        
    }
    
    public Author createRandomAuthor(Integer id) {
        LOG.log(Level.INFO, "Create random Author with id {0}", id);
        //int maxLength = 10;
        Author author = new Author();
        String firstName = "Lasse";//UUID.randomUUID().toString().substring(0, maxLength);
        String lastName = "Holm";//UUID.randomUUID().toString().substring(0, maxLength);
        String bio = "bio";//UUID.randomUUID().toString().substring(0, maxLength);
        String country = "country";//UUID.randomUUID().toString().substring(0, maxLength);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setBio(bio);
        author.setCountry(country);
        author.setId(id);
        return author;
    }
    
    public Author getAuthor(int id) {
        LOG.log(Level.INFO, "GET author with id {0}", id);
        String resourceName = "authors/" + Integer.toString(id);
        Author author = given().accept(ContentType.JSON).get(BASE_URL + resourceName).jsonPath().getObject("author", Author.class);
        return author;
    }
    
    public Response getAuthorResponse(int id) {
        LOG.log(Level.INFO, "GET author Response with id {0}", id);
        String resourceName = "authors/" + id;
        String path = BASE_URL + resourceName;
        LOG.log(Level.INFO, "Path: {0}", path);
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    public Response getAllAuthorsResponse() {
        LOG.log(Level.INFO, "GET all authors Response");
        String resourceName = "authors";
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response getAuthorsByBookIdResponse(int id) {
        LOG.log(Level.INFO, "GET authors of the book with id {0}", id);
        String resourceName = "books/" + Integer.toString(id) + "/authors";
        Response response = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response postAuthorResponse(SingleAuthor singleAuthor) {
        Object[] authorValues = {singleAuthor.getAuthor().getId(), singleAuthor.getAuthor().getFirstName() + 
                " " + singleAuthor.getAuthor().getLastName()};
        LOG.log(Level.INFO, "POST author Response with id {0} and name {1}", authorValues);
        
        String resourceName = "authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).post(BASE_URL + resourceName);
        return response;
    }
    
    public Response postAuthorToBookResponse(int id, SingleAuthor singleAuthor) {
        LOG.log(Level.INFO, "POST the author '{0}'  to the book with id {1}", new Object[]{singleAuthor.getAuthor().getFirstName() + 
                " " + singleAuthor.getAuthor().getLastName(), id});
        
        String resourceName = "books/" + Integer.toString(id) + "/authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).post(BASE_URL + resourceName);
        return response;
    }
    
    public Response putAuthorToBookResponse(int bookId, String jsonString) {
        LOG.log(Level.INFO, "PUT author to book with id {0} Response", bookId);
        String resourceName ="books/" + bookId + "/authors";
        Response response = given().contentType(ContentType.JSON).body(jsonString).put(BASE_URL + resourceName);
        return response;
    }
    
    public Response deleteAuthor(int id) {
        LOG.log(Level.INFO, "DELETE author with id {0}", id);
        String deleteResourceName = "authors/" + id;
        Response deleteResponse = delete(BASE_URL + deleteResourceName);
        return deleteResponse;
    }
    
    public Response putAuthorResponse(SingleAuthor singleAuthor) {
        Object[] authorValues = {singleAuthor.getAuthor().getId(), singleAuthor.getAuthor().getFirstName() + 
                " " + singleAuthor.getAuthor().getLastName()};
        LOG.log(Level.INFO, "PUT author with id {0} and name {1}", authorValues);
        
        String resourceName = "authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).put(BASE_URL + resourceName);
        return response;
    }
    
    public List<Author> getAuthorListFromResponse(Response response, String path) {
        LOG.log(Level.INFO, "get author list from the path {0} from response", path);
        String className = response.jsonPath().getString(path + ".author.getClass()");
          String resource = path + ".author";
          LOG.log(Level.INFO, "className: {0}", className);
          LOG.log(Level.INFO, "authors in json: {0}", response.jsonPath().get(resource).toString());
            List<Author> authorList = new ArrayList<>();
            
             if(className.equals("class java.util.HashMap")) {
                Author author = response.jsonPath().getObject(resource, Author.class);
                authorList.add(author);
             }
             
             if(className.equals("class java.util.ArrayList")) {
                 resource = path + ".author.size()";
                 int s = response.jsonPath().getInt(resource);
                 for(int k = 0; k < s; k++) {
                     resource = path + ".author[" + k + "]";
                     Author author = response.jsonPath().getObject(resource, Author.class);
                     authorList.add(author);     
                 }
             }
             return authorList;
    }
    public List<Author> getAuthorListFromHashMap(List<HashMap> authorHashMapList) {
        LOG.log(Level.INFO, "GET Author list from hashMap");
        List<Author> authorList = new ArrayList<>();
        for(HashMap hm : authorHashMapList) {
            Author author = new Author(hm);
            authorList.add(author);
        }
        return authorList;
    }
    
}

