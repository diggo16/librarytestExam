/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin;

import se.nackademin.resttest.AuthorOperations;
import se.nackademin.resttest.BookOperations;

/**
 *
 * @author daniel
 */
public class BaseTest {
    
    protected final BookOperations bookOperations;
    protected final AuthorOperations authorOperations;
    
    public BaseTest() {
        bookOperations = new BookOperations();
        authorOperations = new AuthorOperations();
    }
    
}
