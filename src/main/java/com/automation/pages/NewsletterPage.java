package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsletterPage extends BasePage {

    private static final String URL = "https://nellybutera.github.io/newsletter/";

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    @FindBy(css = "#success-card h1")
    private WebElement successTitle;

    @FindBy(id = "confirmed-email")
    private WebElement confirmedEmail;

    @FindBy(id = "dismiss-btn")
    private WebElement dismissButton;

    public NewsletterPage(WebDriver driver) {
        super(driver);
    }

    public NewsletterPage open() {
        driver.get(URL);
        return this;
    }

    public NewsletterPage enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    public NewsletterPage submit() {
        submitButton.click();
        return this;
    }

    public NewsletterPage submitWithEmail(String email) {
        return enterEmail(email).submit();
    }

    public NewsletterPage dismiss() {
        dismissButton.click();
        return this;
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isErrorVisible() {
        return errorMessage.isDisplayed();
    }

    public String getSuccessTitle() {
        return successTitle.getText();
    }

    public String getConfirmedEmail() {
        return confirmedEmail.getText();
    }
}
