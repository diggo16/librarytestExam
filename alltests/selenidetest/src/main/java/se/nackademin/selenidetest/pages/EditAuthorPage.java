/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenidetest.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author daniel
 */
public class EditAuthorPage extends MenuPage {
    @FindBy(css = "#gwt-uid-7")
    private SelenideElement firstNameField;
    @FindBy(css = "#gwt-uid-9")
    private SelenideElement lastNameField;
    @FindBy(css = "#gwt-uid-3")
    private SelenideElement countryField;
    @FindBy(css = "#gwt-uid-5")
    private SelenideElement descriptionField;
    @FindBy(css = "#save-author-button")
    private SelenideElement saveAuthorButton;

    public void setFirstNameField(String firstName) {
        setTextFieldValue("first name field", firstName, firstNameField);
    }
    public void setLastNameField(String lastName) {
        setTextFieldValue("last name field", lastName, lastNameField);
    }
    public void setCountryField(String country) {
        setTextFieldValue("last name field", country, countryField);
    }
    public void setDescriptionField(String description) {
        setTextFieldValue("description field", description, descriptionField);
    }
    public void clickSaveAuthorButton() {
        clickButton("save author", saveAuthorButton);
    }
    
}
