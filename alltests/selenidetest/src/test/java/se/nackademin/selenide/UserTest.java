/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import se.nackademin.selenidetest.helpers.UserHelper;
import se.nackademin.selenidetest.model.User;

/**
 *
 * @author daniel
 */
public class UserTest{
    
    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "/home/daniel/seleniumdrivers/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
        
    }
     @Before
    public void setup() {
        open("http://localhost:8080/librarytest/");
    }
    
    @Test
    public void createAndLoginUser() {
        String username = UUID.randomUUID().toString().substring(0, 12);
        String password = UUID.randomUUID().toString().substring(0, 12);
        UserHelper.createNewUser(username, password);
        UserHelper.logInAsUser(username, password);
        sleep(1000);
        assertTrue(UserHelper.isLoggedIn());
        
        UserHelper.signOut();
        assertFalse(UserHelper.isLoggedIn());
    }
    
    @Test
    public void changeFirstNameOnUser() {
        String username = UUID.randomUUID().toString().substring(0, 12);
        String password = UUID.randomUUID().toString().substring(0, 12);
        UserHelper.createNewUser(username, password);
        UserHelper.logInAsUser(username, password);
        
        User user = UserHelper.fetchUser();
        user.setFirstName(UUID.randomUUID().toString().substring(0, 6));
        
        UserHelper.setUser(user);
        
        User newUser = UserHelper.fetchUser();
        
        assertEquals(user.getFirstName(), newUser.getFirstName());
    }
    
}
