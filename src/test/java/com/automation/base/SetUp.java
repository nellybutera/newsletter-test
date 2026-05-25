package com.automation.base;

import com.automation.pages.NewsletterPage;
import com.automation.pages.SuccessModal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(SetUp.Slf4jTestWatcher.class)
public class SetUp {

    private static final Logger log = LoggerFactory.getLogger(SetUp.class);

    protected static WebDriver driver;
    protected static NewsletterPage newsletterPage;
    protected static SuccessModal successModal;

    static class Slf4jTestWatcher implements TestWatcher {

        @Override
        public void testSuccessful(ExtensionContext context) {
            log.info("PASSED: {}", context.getDisplayName());
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            log.error("FAILED: {} — {}", context.getDisplayName(), cause.getMessage());
        }
    }

    @BeforeAll
    static void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = "true".equalsIgnoreCase(System.getProperty("headless"))
                        || "true".equalsIgnoreCase(System.getenv("HEADLESS"))
                        || "true".equalsIgnoreCase(System.getenv("CI"));

        log.info("Starting {} browser (headless={})", browser, headless);

        if ("firefox".equals(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
            }
            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        newsletterPage = new NewsletterPage(driver);
        successModal = new SuccessModal(driver);
        log.info("Browser ready");
    }

    @BeforeEach
    void openPage() {
        newsletterPage.open();
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
        }
    }
}
