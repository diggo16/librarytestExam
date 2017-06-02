package se.nackademin.selenidetest.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class AuthorPage extends MenuPage {
    
    @FindBy(css = "#gwt-uid-3")
    SelenideElement nameField;
    @FindBy(css = "#gwt-uid-5")
    SelenideElement countryField;
    @FindBy(css = "#gwt-uid-7")
    SelenideElement biographyField;
    @FindBy(css = "#edit-author-button")
    SelenideElement editAuthorButton;
    @FindBy(css = "#delete-author-button")
    SelenideElement deleteAuthorButton;
    
    public String getName() {
        return getTextFieldValue("Title field", nameField);
    }

    public String getCountry() {
        return getTextFieldValue("Country field", countryField);
    }

    public String getBiography() {
        return getTextFieldValue("Biography field", biographyField);
    }
    
    public void clickEditAuthorButton() {
        clickButton("edit author", editAuthorButton);
    }
    public void clickDeleteAuthorButton() {
        clickButton("delete author", deleteAuthorButton);
    }
    
}
