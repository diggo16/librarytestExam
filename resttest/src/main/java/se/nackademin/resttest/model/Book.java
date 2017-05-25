package se.nackademin.resttest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book<T> {
    private Integer id;
    private T author;
    private String description;
    private String isbn;
    private Integer nbrPages;
    private String publicationDate;
    private Integer totalNbrCopies;
    private String title;
    
    private List<Author> authorList;
    
    public Book() {
    }

    public Book(HashMap map, T authors) {
        id = (Integer) map.get("id");
        description = (String) map.get("description");
        isbn = (String) map.get("isbn");
        nbrPages = (Integer) map.get("nbrPages");
        publicationDate = (String) map.get("publicationDate");
        totalNbrCopies = (Integer) map.get("totalNbrCopies");
        title = (String) map.get("title");
        this.author = authors;
    }
    public Book(Map map) {
        id = (Integer) map.get("id");
        description = (String) map.get("description");
        isbn = (String) map.get("isbn");
        nbrPages = (Integer) map.get("nbrPages");
        publicationDate = (String) map.get("publicationDate");
        totalNbrCopies = (Integer) map.get("totalNbrCopies");
        title = (String) map.get("title");
        
        String authorClassName = map.get("author").getClass().getSimpleName();
        List<Author> list = new ArrayList<>();
        if(authorClassName.equals("HashMap")) {
            list.add(new Author((HashMap) map.get("author")));
        }
        if(authorClassName.equals("ArrayList")) {
            ArrayList<HashMap> arrList = (ArrayList) map.get("author");
            arrList.forEach((authorHashMap)->{
                list.add(new Author(authorHashMap));
            });
        }
        authorList = list;
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
        return authorList;
    }
    
    public List<Author> getAuthorList() {
        createAuthorList();
        return authorList;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(T author) {
        this.author = author;
    }
    /**
     * @param authors the author to set
     */
    /*public void setAuthor(List<Author> authors) {
        System.out.println("set AuthorList");
        this.authorList = new ArrayList<>();
        this.authorList = authors;
    }*/

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
     * @param nbrPages the nbrPages to set
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
    
    public void createAuthorList() {
        String className = author.getClass().getSimpleName();

        List<Author> authorList = null;
        if(className.equals("ArrayList")) {
            authorList = (List<Author>) author;
        }
        if(className.equals("LinkedTreeMap")) {
            authorList = new ArrayList<>();
            ((Map) author).put("id", ( (Double) ((Map) author).get("id")).intValue());
            Author newAuthor = new Author((Map) author);
            authorList.add(newAuthor);
        }
        this.authorList = authorList;
        
    }
    
}

