package com.automation.tests;

import com.automation.base.SetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public class NewsletterTest extends SetUp {

    // ── Core valid submission ──────────────────────────────────────────────

    @Test
    @Tag("smoke")
    @Tag("positive")
    @DisplayName("TC001 - Valid email shows success modal")
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
    @CsvFileSource(resources = "/testdata/invalid_emails.csv", numLinesToSkip = 1)
    void invalidEmailVariations(String label, String email) {
        newsletterPage.submitWithEmail(email);
        assertAll(
            () -> assertTrue(newsletterPage.isErrorVisible()),
            () -> assertEquals("Valid email required", newsletterPage.getErrorMessage())
        );
    }
}
