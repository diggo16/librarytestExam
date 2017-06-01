/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import se.nackademin.TestBase;
import se.nackademin.selenidetest.helpers.BookHelper;
import se.nackademin.selenidetest.helpers.UserHelper;
import se.nackademin.selenidetest.model.Book;

/**
 *
 * @author daniel
 */
public class BookTest extends TestBase {
    
    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "/home/daniel/seleniumdrivers/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
        getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getWebDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        open("http://localhost:8080/librarytest/");
        String username = UUID.randomUUID().toString().substring(0, 12);
        String password = UUID.randomUUID().toString().substring(0, 12);
        UserHelper.createNewUser(username, password);
        UserHelper.logInAsUser(username, password);
    }
    
    @AfterClass
    public static void tearDownClass() {
        UserHelper.signOut();
    }
    
    @Test
    public void borrowAndReturnBook() {
        String title = "American Gods";
        BookHelper.borrowBook(title);
        
        assertTrue(BookHelper.isBookBorrowed(title));
        BookHelper.returnBook(title);
        assertFalse(BookHelper.isBookBorrowed(title));
    }
    
    @Test
    public void fetchBook() {
        String title = "American Gods";
        Book book = BookHelper.fetchBook(title);
        
        assertNotNull(book);
    }
    
}
