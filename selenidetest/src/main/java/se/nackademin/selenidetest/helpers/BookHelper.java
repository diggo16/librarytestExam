/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenidetest.helpers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import org.openqa.selenium.NoSuchElementException;

import se.nackademin.selenidetest.model.Book;
import se.nackademin.selenidetest.pages.BookPage;
import se.nackademin.selenidetest.pages.BrowseBooksPage;
import se.nackademin.selenidetest.pages.ConfirmDialogPage;
import se.nackademin.selenidetest.pages.EditBookPage;
import se.nackademin.selenidetest.pages.MenuPage;
import se.nackademin.selenidetest.pages.MyProfilePage;

/**
 * @author testautomatisering
 */
public class BookHelper {

 public static Book fetchBook(String searchQuery) {
        searchForBookAndClick(searchQuery);

        BookPage bookPage = page(BookPage.class);
        Book book = new Book();
        book.setTitle(bookPage.getTitle());
        book.setAuthor(bookPage.getAuthor());
        book.setDescription(bookPage.getDescription());
        book.setIsbn(bookPage.getIsbn());
        book.setDatePublished(bookPage.getDatePublished());
        book.setNbrAvailable(Integer.parseInt(bookPage.getnbrAvailable()));
        return book;
    }
    
    public static void setBook(Book book) {
        searchForBookAndClick(book.getTitle());
        
        BookPage bookPage = page(BookPage.class);
        bookPage.clickEditButton();
        
        EditBookPage editBookPage = page(EditBookPage.class);
        editBookPage.setTitleField(book.getTitle());
        editBookPage.setIsbnField(book.getIsbn());
        editBookPage.setNbrAvailableField(Integer.toString(book.getNbrAvailable()));
        editBookPage.setDatePublishedField(book.getDatePublished());
        editBookPage.clickSaveBookButton();
    }
    
    public static void borrowBook(String searchQuery) {
        searchForBookAndClick(searchQuery);
        
        BookPage bookPage = page(BookPage.class);
        bookPage.clickBorrowBookButton();
        
        ConfirmDialogPage confirmDialogPage = page(ConfirmDialogPage.class);
        confirmDialogPage.clickOkButton();
    }
    
    public static boolean isBookBorrowed(String searchQuery) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToMyProfile();
        
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        myProfilePage.clickFirstLoanTitle();
        
        BookPage bookPage = page(BookPage.class);
        return searchQuery.equals(bookPage.getTitle());
    }
    
    public static void returnBook(String searchQuery) {
        searchForBookAndClick(searchQuery);
        
        BookPage bookPage = page(BookPage.class);
        bookPage.clickReturnBookButton();
        
        ConfirmDialogPage confirmDialogPage = page(ConfirmDialogPage.class);
        confirmDialogPage.clickOkButton();
    }
    
    public static String getRandomDatePublished() {
        Random rand = new Random();
        int randomYear = rand.nextInt(110)+1900;
        int dayOfYear = rand.nextInt(364)+1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, randomYear);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String datePublished = format1.format(calendar.getTime());
        return datePublished;
    }
    
    private static void searchForBookAndClick(String title) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToBrowseBooks();
        
        BrowseBooksPage browseBooksPage = page(BrowseBooksPage.class);
        browseBooksPage.setTitleField(title);
        browseBooksPage.clickSearchBooksButton();
        browseBooksPage.clickFirstResultTitle();
    }

    public static void createNewBook(Book bookCreated) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Book createRandomBook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void deleteBook(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
