/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import com.jayway.restassured.response.Response;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.resttest.UserOperations;
import se.nackademin.resttest.model.SingleUser;
import se.nackademin.resttest.model.User;

/**
 *
 * @author daniel
 */
public class UserTest extends BaseTest {
    
    public UserTest() {
    }
    
    @Test
    public void testGetUsers() {
        Response getResponse = UserOperations.getUsersResponse();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        List<User> userList = UserOperations.getUsersFromResponse(getResponse);
        
        userList.forEach((user)->{
            assertNotNull(user);
        });
    }
    
    @Test
    public void testPostUser() {
        User user = UserOperations.createRandomUser();
        Response postResponse = UserOperations.postUserResponse(user);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response getResponse = UserOperations.getUserResponse(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        User actualUser = UserOperations.getUserFromResponse(getResponse);
        
        assertEquals(user.getId(), actualUser.getId());
        assertEquals(user.getDisplayName(), actualUser.getDisplayName());
        assertEquals(user.getFirstName(), actualUser.getFirstName());
        assertEquals(user.getLastName(), actualUser.getLastName());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getPhone(), actualUser.getPhone());
        assertEquals(user.getRole(), actualUser.getRole());
        
        Response deleteResponse = UserOperations.deleteUserResponse(user.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
    }
    
    @Test
    public void testPutUser() {
        User user = UserOperations.createRandomUser();
        Response postResponse = UserOperations.postUserResponse(user);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        String firstName = "Tobias";
        user.setFirstName(firstName);
        
        Response putResponse = UserOperations.putUserResponse(user);
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        Response getResponse = UserOperations.getUserResponse(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        User actualUser = UserOperations.getUserFromResponse(getResponse);
        
        assertEquals(firstName, actualUser.getFirstName());
        
        Response deleteResponse = UserOperations.deleteUserResponse(user.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
    }
    
    @Test
    public void testGetUser() {
        Response getResponse = UserOperations.getUserResponse(13);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
    }
    
    @Test
    public void testDeleteUser() {
        User user = UserOperations.createRandomUser();
        Response postResponse = UserOperations.postUserResponse(user);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Response deleteResponse = UserOperations.deleteUserResponse(user.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
        
        Response getResponse = UserOperations.getUserResponse(user.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
    }
    
}
