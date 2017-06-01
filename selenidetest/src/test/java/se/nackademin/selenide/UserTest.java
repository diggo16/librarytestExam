/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import se.nackademin.TestBase;
import se.nackademin.selenidetest.helpers.UserHelper;
import se.nackademin.selenidetest.model.User;

/**
 *
 * @author daniel
 */
public class UserTest extends TestBase {
    
    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "/home/daniel/seleniumdrivers/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
        getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getWebDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        
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
    }
    @Test
    public void logoutUser() {
        String username = UUID.randomUUID().toString().substring(0, 12);
        String password = UUID.randomUUID().toString().substring(0, 12);
        UserHelper.createNewUser(username, password);
        UserHelper.logInAsUser(username, password);
        UserHelper.signOut();
        sleep(3000);
        assertFalse(UserHelper.isLoggedIn());
    }
    
    @Test
    public void changeUserInfo() {
        String username = UUID.randomUUID().toString().substring(0, 12);
        String password = UUID.randomUUID().toString().substring(0, 12);
        UserHelper.createNewUser(username, password);
        UserHelper.logInAsUser(username, password);
        
        User user = UserHelper.fetchUser();
        user.setFirstName("Magnus");
        user.setLastName("Karlsson");
        user.setEmail("mk@hotmail.com");
        user.setPhone("070-0001111");
        
        UserHelper.setUser(user);
        
        User changedUser = UserHelper.fetchUser();
        
        assertEquals(user.getFirstName(), changedUser.getFirstName());
        assertEquals(user.getLastName(), changedUser.getLastName());
        assertEquals(user.getEmail(), changedUser.getEmail());
        assertEquals(user.getPhone(), changedUser.getPhone());
        
        UserHelper.signOut();
    }
    
}