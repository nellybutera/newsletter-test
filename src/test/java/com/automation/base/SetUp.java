package com.automation.base;

import com.automation.pages.NewsletterPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SetUp {

    protected static WebDriver driver;
    protected static NewsletterPage newsletterPage;

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", System.getenv("HEADLESS"))) ||
            "true".equals(System.getenv("CI"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        newsletterPage = new NewsletterPage(driver);
    }

    @BeforeEach
    void openPage() {
        newsletterPage.open();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
