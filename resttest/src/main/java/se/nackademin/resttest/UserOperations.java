/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static se.nackademin.resttest.BaseOperations.BASE_URL;
import se.nackademin.resttest.model.Book;
import se.nackademin.resttest.model.User;

/**
 *
 * @author daniel
 */
public class UserOperations extends BaseOperations {
    private static final Logger LOG = Logger.getLogger(BookOperations.class.getName());
    
    public static Response getUsersResponse() {
        LOG.info("GET the users");
        
        String resourceName = "users";
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return getResponse;
    }
    
    public static List<User> getUsersFromResponse(Response response) {
       List<HashMap> hashMapList = response.jsonPath().getList("users.user");
       return getUserListFromHashMap(hashMapList);
    }
    
    private static List<User> getUserListFromHashMap(List<HashMap> userHashMapList) {
        LOG.log(Level.INFO, "GET user list from hash map");
        List<User> userList = new ArrayList<>();
        
        userHashMapList.forEach((bookHashMap)->{
           User user = new User(bookHashMap);
           userList.add(user);
        });
        return userList;
    }
    
}
