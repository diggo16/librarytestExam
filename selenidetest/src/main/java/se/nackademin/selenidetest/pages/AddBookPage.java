package se.nackademin.selenidetest.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class AddBookPage extends MenuPage {
    @FindBy(css = "#gwt-uid-3")
    private SelenideElement titleField;
    @FindBy(css = "#gwt-uid-9")
    private SelenideElement descriptionField;
    @FindBy(css = "#gwt-uid-11")
    private SelenideElement nbrPagesField;
    @FindBy(css = "#gwt-uid-13")
    private SelenideElement isbnField;
    @FindBy(css = "#gwt-uid-5")
    private SelenideElement totalNbrCopiesField;
    @FindBy(css = "#gwt-uid-7")
    private SelenideElement datePublishedField;
    @FindBy(css = ".v-select-twincol-options")
    private SelenideElement availableAuthorsOption;
    @FindBy(css = ".v-button")
    private SelenideElement SelectAvailableAuthorButton;
    
    @FindBy(css = "#add-book-button")
    private SelenideElement addBookButton;
    @FindBy(css = ".v-slot span:last-child")// Länken är fel
    private SelenideElement bookAddedTitleText;

    /**
     * @param title the titleField to set
     */
    public void setTitleField(String title) {
        setTextFieldValue("title field", title, titleField);
    }

    /**
     * @param description the descriptionField to set
     */
    public void setDescriptionField(String description) {
        setTextFieldValue("description field", description, descriptionField);
    }

    /**
     * @param nbrPages the nbOfPagesField to set
     */
    public void setNbrPagesField(String nbrPages) {
        setTextFieldValue("nbrPages field", nbrPages, nbrPagesField);
    }

    /**
     * @param isbn the isbnField to set
     */
    public void setIsbnField(String isbn) {
       setTextFieldValue("isbn field", isbn, isbnField);
    }

    /**
     * totalNbrCopies nbOfCopies the nbOfCopiesField to set
     */
    public void setTotalNbrCopiesField(String totalNbrCopies) {
        setTextFieldValue("totalNbrCopies field", totalNbrCopies, totalNbrCopiesField);
    }

    /**
     * @param datePublished the datePublishedField to set
     */
    public void setDatePublishedField(String datePublished) {
        setTextFieldValue("datePublished field", datePublished, datePublishedField);
    }
    
    public void clickAddBookButton() {
        clickButton("add book", addBookButton);
    }

    /**
     * @return the bookAddedTitleText
     */
    public String getBookAddedTitleText() {    
        if(!bookAddedTitleText.isDisplayed()) {
            return "";
        }
        return getTextFieldValue("get book added title text", bookAddedTitleText);
    }
    
    public void selectAvailableAuthorsOption(int index) {
        availableAuthorsOption.selectOption(index);
        clickButton("select author", SelectAvailableAuthorButton);
    }
    
}
