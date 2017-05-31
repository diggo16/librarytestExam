/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenidetest.pages;

import com.codeborne.selenide.ElementsCollection;
import static com.codeborne.selenide.Selenide.sleep;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author testautomatisering
 */
public class AddUserPage extends MenuPage {

    @FindBy(css = "#gwt-uid-3")
    SelenideElement userNameField;
    @FindBy(css = "#gwt-uid-5")
    SelenideElement passwordField;
    @FindBy(css = "input[value=on]")
    SelenideElement radioButtons;   // TODO cant find correct element
    @FindBy(css = "#gwt-uid-16")
    SelenideElement libRadioButton;
    @FindBy(css = "#add-user-button")
    SelenideElement addUserButton;

    public void setUsername(String username) {
        setTextFieldValue("user name field", username, userNameField);
    }

    public void setPassword(String password) {
        setTextFieldValue("password field", password, passwordField);
    }

    public void clickAddUserButton() {
        clickButton("add user button", addUserButton);
    }
    
    public void clickLibrarianRadioButton() {
        if(radioButtons.isDisplayed()) {
            System.out.println("exist");
        }
        clickButton("click librarian radio button", libRadioButton);
        /*if(libRadioButton.exists() && libRadioButton.isEnabled()) {
            System.out.println("exist");
            clickButton("click librarian radio button", libRadioButton);
        } else {
            System.out.println("doesn't exist");
        }*/
        /*ElementsCollection radioButtonsCollection = radioButtons.findAll("input");
        SelenideElement librarianButton = radioButtonsCollection.get(0);
        System.out.println(librarianButton.toString());
        if(librarianButton.isSelected()) {
            System.out.println("Exist");
            librarianButton.click();
            //clickButton("click librarian radio button", radioButtonsCollection.get(0));
        }
        else {
            System.out.println("doesn't exist");
            //radioButtonsCollection.get(1).setSelected(false);
            librarianButton.setSelected(true);
        }*/
        
        /*if(radioButtons.exists())
        {
            System.out.println("Exists");
            if(radioButtons.selectRadio("on").exists()) {
                System.out.println("radioButton exists");
                clickButton("click librarian radio button", radioButtons.selectRadio("on"));
            }
            else {
                System.out.println("radioButton does not exists");
            }
        }
        else {
            System.out.println("Not exists");
        }*/
        //clickButton("click librarian radio button", radioButtons);
        //sleep(3000);
    }
}
