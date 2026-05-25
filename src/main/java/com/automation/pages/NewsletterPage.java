package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsletterPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(NewsletterPage.class);
    private static final String URL = "https://nellybutera.github.io/newsletter/";

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(id = "error-message")
    private WebElement errorMessage;

    public NewsletterPage(WebDriver driver) {
        super(driver);
    }

    public NewsletterPage open() {
        log.info("Navigating to {}", URL);
        driver.get(URL);
        return this;
    }

    public NewsletterPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    public NewsletterPage submit() {
        log.info("Clicking submit");
        submitButton.click();
        return this;
    }

    public NewsletterPage submitWithEmail(String email) {
        return enterEmail(email).submit();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        String text = errorMessage.getText();
        log.info("Error message: {}", text);
        return text;
    }

    public boolean isErrorVisible() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.isDisplayed();
    }
}
