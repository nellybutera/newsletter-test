package com.automation.tests;

import com.automation.base.SetUp;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Newsletter Sign-Up Automation")
@Feature("Email Validation")
public class NewsletterTest extends SetUp {

    // ── Core valid submission ──────────────────────────────────────────────

    @Test
    @Tag("smoke")
    @Tag("positive")
    @DisplayName("TC001 - Valid email shows success modal")
    @Story("Valid email submission")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Standard valid email triggers success modal displaying the submitted address")
    void TC001_validEmailShowsSuccessModal() {
        newsletterPage.submitWithEmail("test@example.com");
        assertAll(
            () -> assertEquals("Thanks for subscribing!", successModal.getTitle()),
            () -> assertEquals("test@example.com", successModal.getConfirmedEmail())
        );
    }

    // ── Core invalid submissions ───────────────────────────────────────────

    @Test
    @Tag("smoke")
    @Tag("negative")
    @DisplayName("TC002 - Malformed email shows error")
    @Story("Invalid email submission")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Plaintext without @ or domain triggers the inline error message")
    void TC002_malformedEmailShowsError() {
        newsletterPage.submitWithEmail("invalid");
        assertAll(
            () -> assertTrue(newsletterPage.isErrorVisible()),
            () -> assertEquals("Valid email required", newsletterPage.getErrorMessage())
        );
    }

    @Test
    @Tag("smoke")
    @Tag("negative")
    @DisplayName("TC003 - Empty email shows error")
    @Story("Invalid email submission")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Submitting the form without entering any email triggers the inline error message")
    void TC003_emptyEmailShowsError() {
        newsletterPage.submit();
        assertAll(
            () -> assertTrue(newsletterPage.isErrorVisible()),
            () -> assertEquals("Valid email required", newsletterPage.getErrorMessage())
        );
    }

    // ── Valid email variations (data-driven) ──────────────────────────────

    @ParameterizedTest(name = "{0}")
    @DisplayName("Valid email variations show success modal")
    @Tag("regression")
    @Tag("positive")
    @Story("Valid email variations")
    @Severity(SeverityLevel.NORMAL)
    @Description("Emails with numbers, hyphens, or underscores in username/domain/extension pass validation")
    @CsvFileSource(resources = "/testdata/valid_emails.csv", numLinesToSkip = 1)
    void validEmailVariations(String label, String email) {
        newsletterPage.submitWithEmail(email);
        assertAll(
            () -> assertEquals("Thanks for subscribing!", successModal.getTitle()),
            () -> assertEquals(email, successModal.getConfirmedEmail())
        );
    }

    // ── Invalid email variations (data-driven) ────────────────────────────

    @ParameterizedTest(name = "{0}")
    @DisplayName("Invalid email variations show error message")
    @Tag("regression")
    @Tag("negative")
    @Story("Invalid email variations")
    @Severity(SeverityLevel.NORMAL)
    @Description("Emails with spaces, missing parts, or missing @ symbol are rejected with an inline error")
    @CsvFileSource(resources = "/testdata/invalid_emails.csv", numLinesToSkip = 1)
    void invalidEmailVariations(String label, String email) {
        newsletterPage.submitWithEmail(email);
        assertAll(
            () -> assertTrue(newsletterPage.isErrorVisible()),
            () -> assertEquals("Valid email required", newsletterPage.getErrorMessage())
        );
    }
}
