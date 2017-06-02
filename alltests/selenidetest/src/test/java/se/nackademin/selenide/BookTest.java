/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.selenidetest.helpers.BookHelper;
import se.nackademin.selenidetest.model.Book;

/**
 *
 * @author daniel
 */
public class BookTest extends TestBase {
    
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
        
        assertEquals(title, book.getTitle());
        assertEquals("Neil Gaiman", book.getAuthor());
        assertEquals("0-380-97365-0", book.getIsbn());
        assertEquals("2001-07-01", book.getDatePublished());
        
  
    }
    
}
