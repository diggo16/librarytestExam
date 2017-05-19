package se.nackademin.resttest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book {
    private Integer id;
    private List<Author> author;
    private String description;
    private String isbn;
    private Integer nbrPages;
    private String publicationDate;
    private Integer totalNbrCopies;
    private String title;
    
    public Book() {
        author = new ArrayList<>();
    }

    public Book(HashMap map, List<Author> authors) {
        id = (Integer) map.get("id");
        description = (String) map.get("description");
        isbn = (String) map.get("isbn");
        nbrPages = (Integer) map.get("nbrPages");
        publicationDate = (String) map.get("publicationDate");
        totalNbrCopies = (Integer) map.get("totalNbrCopies");
        title = (String) map.get("title");
        this.author = authors;      
    }

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
     * @return the author
     */
    public List<Author> getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author.clear();
        this.author.add(author);
    }
    /**
     * @param authors the author to set
     */
    public void setAuthor(List<Author> authors) {
        this.author = authors;
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
     * @return the nbOfPage
     */
    public Integer getNbrPages() {
        return nbrPages;
    }

    /**
     * @param nbOfPage the nbOfPage to set
     */
    public void setNbrPages(Integer nbrPages) {
        this.nbrPages = nbrPages;
    }

    /**
     * @return the publicationDate
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * @param publicationDate the publicationDate to set
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * @return the totalNbrCopies
     */
    public Integer getTotalNbrCopies() {
        return totalNbrCopies;
    }

    /**
     * @param totalNbrCopies the totalNbrCopies to set
     */
    public void setTotalNbrCopies(Integer totalNbrCopies) {
        this.totalNbrCopies = totalNbrCopies;
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
    
}

