package se.nackademin.resttest.model;

import java.util.ArrayList;
import java.util.Map;

public class Author {
    private Integer id;
    private String bio;
    private String country;
    private String firstName;
    private String lastName;
    
    public Author() {
        
    }

    public Author(Map<String, Object> map) {
        id = (Integer) map.get("id");
        bio = (String) map.get("bio");
        country = (String) map.get("country");
        firstName = (String) map.get("firstName");
        lastName = (String) map.get("lastName");
    }

    public Author(Author author) {
        id = author.id;
        bio = author.bio;
        country = author.country;
        firstName = author.firstName;
        lastName = author.lastName;
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
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio the bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) {
            return false;
        }
        else {
            Author author = (Author) other;
            if(id == author.id) {
                return true;
            }
        }
        return false;
    }

}

