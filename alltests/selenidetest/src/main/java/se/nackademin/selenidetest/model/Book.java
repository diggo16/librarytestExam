/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenidetest.model;

/**
 * @author testautomatisering
 */
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private String datePublished;
    private Integer totalNbrCopies;
    private Integer nbrPages;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the datePublished
     */
    public String getDatePublished() {
        return datePublished;
    }

    /**
     * @param datePublished the datePublished to set
     */
    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    /**
     * @return the nbrAvailable
     */
    public Integer getTotalNbrCopies() {
        return totalNbrCopies;
    }

    /**
     * @param totalNbrCopies the nbrAvailable to set
     */
    public void setTotalNbrCopies(Integer totalNbrCopies) {
        this.totalNbrCopies = totalNbrCopies;
    }

    /**
     * @return the nbrPages
     */
    public Integer getNbrPages() {
        return nbrPages;
    }

    /**
     * @param nbrPages the nbrPages to set
     */
    public void setNbrPages(Integer nbrPages) {
        this.nbrPages = nbrPages;
    }
}
