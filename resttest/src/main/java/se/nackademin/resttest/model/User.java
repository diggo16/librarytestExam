/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest.model;

import java.util.Map;

/**
 *
 * @author daniel
 */
public class User {
    
    private Integer id;
    private String displayName;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String role;
    
    public User() {
        
    }
    
    public User(Map userMap) {
        displayName = (String) userMap.get("displayName");
        password = (String) userMap.get("password");
        role = (String) userMap.get("role");
        
        if(userMap.containsKey("id")) {
            id = (Integer) userMap.get("id");
        }
        if(userMap.containsKey("firstName")) {
            firstName = (String) userMap.get("firstName");
        }
        if(userMap.containsKey("lastName")) {
            lastName = (String) userMap.get("lastName");
        }
        if(userMap.containsKey("phone")) {
            phone = (String) userMap.get("phone");
        }
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
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        String str = "{" + id + ", " + displayName + ", " + firstName + ", " + lastName +
                ", " + password + ", " + phone + ", " + role + "}";
        return str;
    }
    
}
