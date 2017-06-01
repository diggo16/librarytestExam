/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class BaseOperations {
    protected static final String BASE_URL = "http://localhost:8080/librarytest-rest/";    
    
    public static int getRandomId() {
        return new Random().nextInt(10000) + 1000;
    }
}
