/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import org.junit.Test;
import static org.junit.Assert.*;
import se.nackademin.selenidetest.helpers.AuthorHelper;
import se.nackademin.selenidetest.model.Author;

/**
 *
 * @author daniel
 */
public class AuthorTest extends TestBase {
    
    @Test
    public void fetchAuthor() {
        String name = "Neil Gaiman";
        String country = "Great Britain";
        Author author = AuthorHelper.fetchAuthor(name, "");
        
        assertEquals(name, author.getName());
        assertEquals(country, author.getCountry());
    }
    
}
