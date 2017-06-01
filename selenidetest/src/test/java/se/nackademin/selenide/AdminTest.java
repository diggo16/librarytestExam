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
import org.junit.AfterClass;
import org.junit.Ignore;
import se.nackademin.selenidetest.helpers.BookHelper;
import se.nackademin.selenidetest.helpers.UserHelper;
import se.nackademin.selenidetest.model.Book;
import se.nackademin.selenidetest.model.User;
import se.nackademin.selenidetest.model.Author;
import se.nackademin.TestBase;
import se.nackademin.selenidetest.helpers.AuthorHelper;

/**
 *
 * @author daniel
 */
public class AdminTest{
    
    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "/home/daniel/seleniumdrivers/chromedriver");
        System.setProperty("selenide.browser", "Chrome");
        open("http://localhost:8080/librarytest/");
        UserHelper.logInAsAdmin();
    }

    @AfterClass
    public static void teardownClass() {
        UserHelper.signOut();
    }
    
    @Test
    public void addAuthor() {
        Author author = AuthorHelper.createRandomAuthor();
        AuthorHelper.addNewAuthor(author);
        
        Author fetchedAuthor = AuthorHelper.fetchAuthor(author.getName(), author.getCountry());
        
        assertEquals(author.getName(), fetchedAuthor.getName());
        assertEquals(author.getCountry(), fetchedAuthor.getCountry());
        assertEquals(author.getBiography(), fetchedAuthor.getBiography());
    }

    @Test
    public void addBook() {
        Book book = new Book();
        String title = UUID.randomUUID().toString().substring(0, 15);
        String description = "description 1 2 3.";
        String datePublished = "2000-01-02";
        int totalNbrCopies = 5;
        book.setTitle(title);
        book.setDescription(description);
        book.setDatePublished(datePublished);
        book.setTotalNbrCopies(totalNbrCopies);
        BookHelper.AddBook(book);
        
        Book fetchedBook = BookHelper.fetchBook(book.getTitle());
        System.out.println("title: " + fetchedBook.getTitle());
        System.out.println("DatePublished: " + fetchedBook.getDatePublished());
        System.out.println("TotalNbrCopies: " + fetchedBook.getTotalNbrCopies());
        System.out.println("Description: " + fetchedBook.getDescription());
        sleep(3000);
        assertEquals(book.getTitle(),fetchedBook.getTitle());
        assertEquals(book.getDatePublished(),fetchedBook.getDatePublished());
        assertEquals(book.getTotalNbrCopies(),fetchedBook.getTotalNbrCopies());
        assertEquals(book.getDescription(),fetchedBook.getDescription());
    }
}
