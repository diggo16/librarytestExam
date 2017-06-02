/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class BaseOperations {
    protected static final String BASE_URL = "http://localhost:8080/librarytest-rest/";    
    
    public static int getRandomId() {
        return new Random().nextInt(10000) + 100000;
    }
    protected static Response getResponse(Logger LOG, String resourceName) {
        LOG.log(Level.INFO, "GET {0}", resourceName);
        
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourceName);
        return getResponse;
    }
}
