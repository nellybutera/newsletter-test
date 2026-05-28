package com.automation.tests;

import com.automation.base.SetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class NewsletterTest extends SetUp {

    // ── Core valid submission ──────────────────────────────────────────────

    @Test
    @DisplayName("TC001 - Valid email shows success modal")
    void TC001_validEmailShowsSuccessModal() {
        newsletterPage.submitWithEmail("test@example.com");
        assertEquals("Thanks for subscribing!", successModal.getTitle());
        assertEquals("test@example.com", successModal.getConfirmedEmail());
    }

    // ── Core invalid submissions ───────────────────────────────────────────

    @Test
    @DisplayName("TC002 - Malformed email shows error")
    void TC002_malformedEmailShowsError() {
        newsletterPage.submitWithEmail("invalid");
        assertTrue(newsletterPage.isErrorVisible());
        assertEquals("Valid email required", newsletterPage.getErrorMessage());
    }

    @Test
    @DisplayName("TC003 - Empty email shows error")
    void TC003_emptyEmailShowsError() {
        newsletterPage.submit();
        assertTrue(newsletterPage.isErrorVisible());
        assertEquals("Valid email required", newsletterPage.getErrorMessage());
    }

    // ── Valid email variations (data-driven) ──────────────────────────────

    @ParameterizedTest(name = "{0}")
    @DisplayName("Valid email variations show success modal")
    @CsvSource({
        "'TC005 - numbers in username',    user123@example.com",
        "'TC006 - underscore in username', user_test@example.com",
        "'TC008 - numbers in domain',      user@123.com",
        "'TC009 - hyphen in domain',       user@my-domain.com",
        "'TC012 - numbers in extension',   user@example.123",
        "'TC013 - hyphen in extension',    user@example.co-m"
    })
    void validEmailVariations(String label, String email) {
        newsletterPage.submitWithEmail(email);
        assertEquals("Thanks for subscribing!", successModal.getTitle());
        assertEquals(email, successModal.getConfirmedEmail());
    }

    // ── Invalid email variations (data-driven) ────────────────────────────

    @ParameterizedTest(name = "{0}")
    @DisplayName("Invalid email variations show error message")
    @CsvSource({
        "'TC004 - space in username',       'john doe@example.com'",
        "'TC007 - empty username',          @example.com",
        "'TC010 - space in domain',         'user@my domain.com'",
        "'TC011 - empty domain',            user@.com",
        "'TC014 - space in extension',      'user@example.c om'",
        "'TC015 - empty extension',         user@example.",
        "'TC016 - missing @ symbol',        userexample.com",
        "'TC017 - missing dot in domain',   user@examplecom"
    })
    void invalidEmailVariations(String label, String email) {
        newsletterPage.submitWithEmail(email);
        assertTrue(newsletterPage.isErrorVisible());
        assertEquals("Valid email required", newsletterPage.getErrorMessage());
    }
}
