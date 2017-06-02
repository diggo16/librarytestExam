/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.delete;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.exception.JsonPathException;
import com.jayway.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nackademin.resttest.model.single.SingleUser;
import se.nackademin.resttest.model.User;

/**
 *
 * @author daniel
 */
public class UserOperations extends BaseOperations {
    private static final Logger LOG = Logger.getLogger(UserOperations.class.getName());
    
    public static Response getUsersResponse() {
        return getResponse(LOG, "users");
    }
    
    public static Response getUserResponse(int id) {       
        String resourceName = "users/" + id;
        return getResponse(LOG, resourceName);
    }
    
    public static List<User> getUsersFromResponse(Response response) {
        LOG.log(Level.INFO, "GET user hash map list from response");
        List<HashMap> hashMapList = response.jsonPath().getList("users.user");
        return getUserListFromHashMap(hashMapList);
    }
    
    public static Response postUserResponse(User user) {
        LOG.log(Level.INFO, "POST user {0}", user.toString());
        SingleUser singleUser = new SingleUser(user);
        String resourceName = "users";
        Response postResponse = given().contentType(ContentType.JSON).body(singleUser).post(BASE_URL + resourceName);
        return postResponse;
    }
    
    public static Response putUserResponse(User user) {
        LOG.log(Level.INFO, "POST user {0}", user.toString());
        SingleUser singleUser = new SingleUser(user);
        String resourceName = "users";
        Response putResponse = given().contentType(ContentType.JSON).body(singleUser).put(BASE_URL + resourceName);
        return putResponse;
    }
    
    public static Response deleteUserResponse(int id) {
        String resourceName = "users/" + id;
        LOG.log(Level.INFO, "delete {0}", resourceName);
        return delete(BASE_URL + resourceName);
    }
    
    public static User createRandomUser() {
        LOG.log(Level.INFO, "Create random user");
        int id = new Random().nextInt(1000) + 500;
        String displayName = "u" + UUID.randomUUID().toString().substring(0, 12);
        String firstName = "f" + UUID.randomUUID().toString().substring(0, 8);
        String lastName = "l" + UUID.randomUUID().toString().substring(0, 12);
        String password = "p" + UUID.randomUUID().toString().substring(0, 16);
        String phone = "p" + UUID.randomUUID().toString().substring(0, 11);
        String role = "LOANER";
        
        User user = new User();
        user.setId(id);
        user.setDisplayName(displayName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRole(role);
        
        return user;
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
    
    public static User getUserFromResponse(Response response) {
        LOG.log(Level.INFO, "GET user from response");
        try {
            return response.jsonPath().getObject("user", User.class);
        } catch(JsonPathException e) {
            LOG.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
  
}
