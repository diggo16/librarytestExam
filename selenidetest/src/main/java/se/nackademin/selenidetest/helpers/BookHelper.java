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
import java.util.UUID;
import org.openqa.selenium.NoSuchElementException;

import se.nackademin.selenidetest.model.Book;
import se.nackademin.selenidetest.pages.AddBookPage;
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
        book.setTotalNbrCopies(Integer.parseInt(bookPage.getnbrAvailable()));
        return book;
    }
    
    public static void setBook(Book book, String searchQuery) {
        searchForBookAndClick(searchQuery);
        
        BookPage bookPage = page(BookPage.class);
        bookPage.clickEditButton();
        
        EditBookPage editBookPage = page(EditBookPage.class);
        editBookPage.setTitleField(book.getTitle());
        
        editBookPage.setNbrAvailableField(Integer.toString(book.getTotalNbrCopies()));
        editBookPage.setDatePublishedField(book.getDatePublished());
        if(book.getIsbn() != null) {
            editBookPage.setIsbnField(book.getIsbn());
        }
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

    public static void AddBook(Book bookCreated) {
         MenuPage menuPage = page(MenuPage.class);
         menuPage.navigateToAddBook();
         
         AddBookPage addBookPage = page(AddBookPage.class);
         addBookPage.setTitleField(bookCreated.getTitle());
         addBookPage.setTotalNbrCopiesField(Integer.toString(bookCreated.getTotalNbrCopies()));
         addBookPage.setDatePublishedField(bookCreated.getDatePublished());
         
         addBookPage.selectAvailableAuthorsOption(1);
         
         String description = bookCreated.getDescription();
         if(description != null) {
             addBookPage.setDescriptionField(description);
         }
         
         String Isbn = bookCreated.getIsbn();
         if(Isbn != null) {
             addBookPage.setIsbnField(Isbn);
         }
 
         Integer nbrPages = bookCreated.getNbrPages();
         if(nbrPages != null) {
             addBookPage.setDescriptionField(Integer.toString(nbrPages));
         }
        
         
         addBookPage.clickAddBookButton();
    }
    
    public static boolean isBookAdded(String title) {
        AddBookPage addBookPage = page(AddBookPage.class);
        System.out.println("expected: " + title);
        System.out.println("actual: " + addBookPage.getBookAddedTitleText());
        return title.equals(addBookPage.getBookAddedTitleText());
    }

    public static Book createRandomBook() {
        int max = 10;
        String title = UUID.randomUUID().toString().substring(0, max);
        String description = UUID.randomUUID().toString().substring(0, max);
        String datePublished = getRandomDatePublished();
        int totalNbrCopies = 5;
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setDatePublished(datePublished);
        book.setTotalNbrCopies(totalNbrCopies);
        
        return book;
    }

    public static void deleteBook(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
