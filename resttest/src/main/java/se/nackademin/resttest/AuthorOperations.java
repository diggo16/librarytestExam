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
import java.util.logging.Logger;
import se.nackademin.resttest.model.Author;
import se.nackademin.resttest.model.SingleAuthor;

public class AuthorOperations {
    
    private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
    private static final Logger LOG = Logger.getLogger(AuthorOperations.class.getName());   // TODO log methods
    
    public AuthorOperations() {
        
    }
    
    public SingleAuthor createRandomSingleAuthor(int id) {
        Author author = createRandomAuthor(id);
        SingleAuthor singleAuthor = new SingleAuthor(author);
        return singleAuthor;        
    }
    
    public Author createRandomAuthor(Integer id) {
        Author author = new Author();
        String name = UUID.randomUUID().toString();
        author.setName(name);
        author.setId(id);
        return author;
    }
    
    public Author getAuthor(int id) {
        String resourceName = "authors/" + Integer.toString(id);
        Author author = given().accept(ContentType.JSON).get(BASE_URL + resourceName).jsonPath().getObject("author", Author.class);
        return author;
    }
    
    public Response getAuthorResponse(int id) {
        String resourceName = "authors/" + id;
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    public Response getAllAuthorsResponse() {
        String resourceName = "authors";
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response getAuthorsByBookIdResponse(int id) {
        String resourceName = "books/" + Integer.toString(id) + "/authors";
        Response response = given().contentType(ContentType.JSON).get(BASE_URL + resourceName);
        return response;
    }
    
    public Response postAuthorResponse(SingleAuthor singleAuthor) {
        String resourceName = "authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).post(BASE_URL + resourceName);
        return response;
    }
    
    public Response postAuthorToBookResponse(int id, SingleAuthor singleAuthor) {
        String resourceName = "books/" + Integer.toBinaryString(id) + "/authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).post(BASE_URL + resourceName);
        return response;
    }
    
    public HashMap<String, Object> convertAuthorToHashMap(Author author) {
        HashMap<String, Object> authorMap = new HashMap<>();
        authorMap.put("name", author.getName());
        authorMap.put("id", author.getId());
        return authorMap;
    }
    
    public Map<String, ArrayList> deleteAuthorFromMap(String name, Map<String, ArrayList> authorsMap) {
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
    
    public String getJsonAuthorString(Integer id, String name) {
        
        String jsonString;
        if(id == null) {
           String jsonStringTemplate = "{\"authors\":\n" +
                "    {\n" +
                "      \"author\": {\n" +
                "      \"name\": \"%s\"\n" +
                "      }\n" +
                "    }\n" +
                "}"; 
           jsonString = String.format(jsonStringTemplate, name);
        }
        else
        {
            String jsonStringTemplate = "{\"authors\":\n" +
                "    {\n" +
                "      \"author\": {\n" +
                "      \"id\": %s,\n" +
                "      \"name\": \"%s\"\n" +
                "      }\n" +
                "    }\n" +
                "}";
            jsonString = String.format(jsonStringTemplate, id, name);
        }
        return jsonString;
    }
    
    public Response putAuthorToBookResponse(int bookId, String jsonString) {
        String resourceName ="books/" + bookId + "/authors";
        Response response = given().contentType(ContentType.JSON).body(jsonString).put(BASE_URL + resourceName);
        return response;
    }
    
    public Response deleteAuthor(int id) {
        String deleteResourceName = "authors/" + id;
        Response deleteResponse = delete(BASE_URL + deleteResourceName);
        return deleteResponse;
    }
    
    public Response putAuthorResponse(SingleAuthor singleAuthor) {
        String resourceName = "authors";
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).put(BASE_URL + resourceName);
        return response;
    }
    
}

