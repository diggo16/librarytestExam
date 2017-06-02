/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide;

import static com.codeborne.selenide.Selenide.open;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import se.nackademin.selenidetest.helpers.AuthorHelper;
import se.nackademin.selenidetest.helpers.UserHelper;
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
