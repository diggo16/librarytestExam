/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest.model;

import java.util.ArrayList;
import java.util.Map;

public class AuthorsMap {
    private Map<String, ArrayList> authors;
    
    public AuthorsMap(Map<String, ArrayList> map) {
        authors = map;
    }

    /**
     * @return the authors
     */
    public Map<String, ArrayList> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(Map<String, ArrayList> authors) {
        this.authors = authors;
    }
    
}

