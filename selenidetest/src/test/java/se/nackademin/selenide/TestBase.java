/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import static com.codeborne.selenide.Selenide.open;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import se.nackademin.selenidetest.helpers.UserHelper;

/**
 *
 * @author daniel
 */
public class TestBase {
    
    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "/home/daniel/seleniumdrivers/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
        open("http://localhost:8080/librarytest/");
        String username = UUID.randomUUID().toString().substring(0, 12);
        String password = UUID.randomUUID().toString().substring(0, 12);
        UserHelper.createNewUser(username, password);
        UserHelper.logInAsUser(username, password);
    }
    @Before
    public void setup() {
        open("http://localhost:8080/librarytest/");
    }
    @AfterClass
    public static void tearDownClass() {
        UserHelper.signOut();
    }
    
}
