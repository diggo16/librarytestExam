/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest.model;

/**
 *
 * @author daniel
 */
public class Loan {
    private User user;
    private Book book;
    private String dateBorrowed;
    private String dateDue;
    
    public Loan() {
        
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return the dateBorrowed
     */
    public String getDateBorrowed() {
        return dateBorrowed;
    }

    /**
     * @param dateBorrowed the dateBorrowed to set
     */
    public void setDateBorrowed(String dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    /**
     * @return the dateDue
     */
    public String getDateDue() {
        return dateDue;
    }

    /**
     * @param dateDue the dateDue to set
     */
    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }
    
}
