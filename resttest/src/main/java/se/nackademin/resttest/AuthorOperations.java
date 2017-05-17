package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.SingleAuthor;

public class AuthorOperations extends BaseOperations {
    
    private static final Logger LOG = Logger.getLogger(AuthorOperations.class.getName());
    
    public AuthorOperations() {
        
    }
    
    public SingleAuthor createRandomSingleAuthor(int id) {
        LOG.log(Level.INFO, "Create random SingleAuthor with id {0}", id);
        Author author = createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        return singleAuthor;        
    }
    
    public Author createRandomAuthor(Integer id) {
        LOG.log(Level.INFO, "Create random Author with id {0}", id);
        Author author = new Author();
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        String bio = UUID.randomUUID().toString();
        String country = UUID.randomUUID().toString();
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
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    public Response getAllAuthorsResponse() {
        LOG.log(Level.INFO, "GET all authors Response");
        String resourceName = "authors";
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response getAuthorsByBookIdResponse(int id) {
        LOG.log(Level.INFO, "GET authors of the book with id {0}", id);
        String resourceName = "books/" + Integer.toString(id) + "/authors";
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
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
        String resourceName = "books/" + Integer.toBinaryString(id) + "/authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).post(BASE_URL + resourceName);
        return response;
    }
    
    /*public HashMap<String, Object> convertAuthorToHashMap(Author author) {
        Object[] authorValues = {author.getId(), author.getFirstName() + " " + author.getLastName()};
        LOG.log(Level.INFO, "Convert the author with the id {0} and name {1} to a HashMap", authorValues);
        HashMap<String, Object> authorMap = new HashMap<>();
        authorMap.put("name", author.getName());
        authorMap.put("id", author.getId());
        return authorMap;
    }*/
    
    public Map<String, ArrayList> deleteAuthorFromMap(String name, Map<String, ArrayList> authorsMap) {
        LOG.log(Level.INFO, "Delete author {0} from the Map", name);
        String key = "author";
        for(int i = 0; i < authorsMap.get(key).size(); i++) {
            HashMap<String, String> tempMap = (HashMap<String, String>) authorsMap.get(key).get(i);
            if(tempMap.get("name").equals(name))
            {
                authorsMap.get(key).remove(i);
            }
        }
        return authorsMap;
    }
    
    public String getJsonAuthorString(Author author) {
        Object[] authorValues = {author.getId(), author.getBio(), author.getCountry(), author.getFirstName(), author.getLastName()};
        LOG.log(Level.INFO, "Convert author with the id {0} and name {1} to a json string", authorValues);
        String jsonString;
        if(author.getId() == null) {
           String jsonStringTemplate = "{\n" +
                "  \"author\": {\n" +
                "    \"bio\": \"%s\",\n" +
                "    \"country\": \"%s\",\n" +
                "    \"firstName\": \"%s\",\n" +
                "    \"lastName\": \"%s\"\n" +
                "  }\n" +
                "}";
           jsonString = String.format(jsonStringTemplate, author.getBio(), author.getCountry(), author.getFirstName(), author.getLastName());
        }
        else
        {
            String jsonStringTemplate = "{\n" +
                "  \"author\": {\n" +
                "    \"id\": %s,\n" +
                "    \"bio\": \"%s\",\n" +
                "    \"country\": \"%s\",\n" +
                "    \"firstName\": \"%s\",\n" +
                "    \"lastName\": \"%s\"\n" +
                "  }\n" +
                "}";
            jsonString = String.format(jsonStringTemplate, author.getId(), author.getBio(), author.getCountry(), author.getFirstName(), author.getLastName());
        }
        return jsonString;
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
    
}

