package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuccessModal extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(SuccessModal.class);

    @FindBy(css = "#success-card h1")
    private WebElement title;

    @FindBy(id = "confirmed-email")
    private WebElement confirmedEmail;

    @FindBy(id = "dismiss-btn")
    private WebElement dismissButton;

    public SuccessModal(WebDriver driver) {
        super(driver);
    }

    public boolean isVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(title));
            return title.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(title));
        String text = title.getText();
        log.info("Success modal title: {}", text);
        return text;
    }

    public String getConfirmedEmail() {
        wait.until(ExpectedConditions.visibilityOf(confirmedEmail));
        return confirmedEmail.getText();
    }

    public void dismiss() {
        log.info("Dismissing success modal");
        dismissButton.click();
    }
}
