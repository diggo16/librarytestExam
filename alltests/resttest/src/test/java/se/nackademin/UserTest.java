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
public class UserTest {

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
    public void testPostUserWithTakenDisplayName() {
        User user = UserOperations.createRandomUser();
        user.setDisplayName("admin");
        Response postResponse = UserOperations.postUserResponse(user);
        assertEquals("Status code should be 400", 400, postResponse.statusCode());
        
        Response getResponse = UserOperations.getUserResponse(user.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
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
    public void testPutUserWithTakenDisplayName() {
        User user = UserOperations.createRandomUser();
        Response postResponse = UserOperations.postUserResponse(user);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        String oldFirstName = user.getFirstName();
        user.setDisplayName("admin");
        user.setFirstName("Tobias");
        
        Response putResponse = UserOperations.putUserResponse(user);
        assertEquals("Status code should be 400", 400, putResponse.statusCode());
        
        Response getResponse = UserOperations.getUserResponse(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        User actualUser = UserOperations.getUserFromResponse(getResponse);
        
        assertEquals(oldFirstName, actualUser.getFirstName());
        
        Response deleteResponse = UserOperations.deleteUserResponse(user.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());
    }

    @Test
    public void testGetUser() {
        Response getResponse = UserOperations.getUserResponse(13);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        User user = UserOperations.getUserFromResponse(getResponse);
        assertNotNull(user);
    }
    
    @Test
    public void testGetUserWithInvalidId() {
        Response getResponse = UserOperations.getUserResponse(-1);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
        User user = UserOperations.getUserFromResponse(getResponse);
        assertNull(user);
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
    
    @Test
    public void testDeleteUserWithInvalidId() {
        Response deleteResponse = UserOperations.deleteUserResponse(-1);
        assertEquals("Status code should be 404", 404, deleteResponse.statusCode());

    }
    
}
