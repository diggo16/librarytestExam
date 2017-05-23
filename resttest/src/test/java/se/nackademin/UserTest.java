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
    
}
