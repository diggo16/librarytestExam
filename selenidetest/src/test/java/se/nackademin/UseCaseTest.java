/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import java.util.UUID;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.selenidetest.helpers.BookHelper;
import se.nackademin.selenidetest.helpers.UserHelper;
import se.nackademin.selenidetest.model.Book;
import se.nackademin.selenidetest.model.User;

/**
 *
 * @author daniel
 */
public class UseCaseTest  extends TestBase {
    
    public UseCaseTest(){

    }
    
    @Test
    public void testGetBook() {
        String title = "Guards!Guards!";
        Book book = BookHelper.fetchBook(title);
        assertEquals(book.getTitle(), title);
    }
    
    @Test
    public void testAddBook() {
        UserHelper.logInAsAdmin();
        Book createdBook = BookHelper.createRandomBook();
        BookHelper.createNewBook(createdBook);
        
        Book fetchedBook = BookHelper.fetchBook(createdBook.getTitle());
        
        assertEquals(createdBook.getTitle(), fetchedBook.getTitle());
        
        UserHelper.signOut();
    }
    
    @Test
    public void testDeleteBook() {
        UserHelper.logInAsAdmin();
        Book createdBook = BookHelper.createRandomBook();
        BookHelper.createNewBook(createdBook);
        
        int id = createdBook.getId();
        BookHelper.deleteBook(id);
        
        Book fetchedBook = BookHelper.fetchBook(createdBook.getTitle());
        
        assertNull(fetchedBook);
        
        UserHelper.signOut();
    }
    
    @Test
    public void testEditBookTitle() {
        UserHelper.logInAsAdmin();
        Book createdBook = BookHelper.createRandomBook();
        BookHelper.createNewBook(createdBook);
        
        String title = "Hello man";
        createdBook.setTitle(title);
        BookHelper.setBook(createdBook);
        
        Book fetchedBook = BookHelper.fetchBook(createdBook.getTitle());
        
        assertEquals(createdBook.getTitle(),fetchedBook.getTitle());
        
        UserHelper.signOut();
    }
    
    @Test
    public void testLoginUser() {
        String username = "thisUser";
        String password = "1234567";
        UserHelper.createNewUser(username, password);
        
        UserHelper.logInAsUser(username, password);
        
        assertTrue(UserHelper.isLoggedIn());
        
        UserHelper.signOut();
    }
    
    @Test
    public void testCreateUser() {
        UserHelper.createNewUser("thisUser", "1234567");
    }
    
    @Test
    public void testChangeEmailOnUser() {
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        UserHelper.createNewUser(username, password);
        
        UserHelper.logInAsUser(username, password);
        
        User oldUser = UserHelper.fetchUser();
        String email = UUID.randomUUID().toString().substring(0, 6) + "@hotmail.com";
        oldUser.setEmail(email);
        UserHelper.setUser(oldUser);

        User newUser = UserHelper.fetchUser();
        
        assertEquals("emails should be the same", email, newUser.getEmail());
        
        UserHelper.signOut();   
        assertFalse("Should be logged out", UserHelper.isLoggedIn());
    }
    
    @Test
    public void testDeleteUser() {
        UserHelper.logInAsAdmin();
    }
    
    @After
    public void tearDown() {
        UserHelper.signOut();
    }
    
}
