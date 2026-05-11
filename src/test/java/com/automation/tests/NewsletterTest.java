package com.automation.tests;

import com.automation.base.SetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewsletterTest extends SetUp {

    // ── Valid submissions ──────────────────────────────────────────────────

    @Test
    @DisplayName("TC001 - Valid email shows success modal")
    void TC001_validEmailShowsSuccessModal() {
        newsletterPage.submitWithEmail("test@example.com");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
        assertEquals("test@example.com", newsletterPage.getConfirmedEmail());
    }

    @Test
    @DisplayName("TC005 - Email with numbers in username is accepted")
    void TC005_emailWithNumbersInUsernameAccepted() {
        newsletterPage.submitWithEmail("user123@example.com");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
    }

    @Test
    @DisplayName("TC006 - Email with underscore in username is accepted")
    void TC006_emailWithUnderscoreInUsernameAccepted() {
        newsletterPage.submitWithEmail("user_test@example.com");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
    }

    // ── Invalid / empty input ──────────────────────────────────────────────

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

    @Test
    @DisplayName("TC004 - Email with space in username shows error")
    void TC004_emailWithSpaceInUsernameShowsError() {
        newsletterPage.submitWithEmail("john doe@example.com");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC007 - Email with empty username shows error")
    void TC007_emptyUsernameShowsError() {
        newsletterPage.submitWithEmail("@example.com");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC008 - Email with numbers in domain is accepted")
    void TC008_numbersInDomainAccepted() {
        newsletterPage.submitWithEmail("user@123.com");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
    }

    @Test
    @DisplayName("TC009 - Email with hyphen in domain is accepted")
    void TC009_hyphenInDomainAccepted() {
        newsletterPage.submitWithEmail("user@my-domain.com");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
    }

    @Test
    @DisplayName("TC010 - Email with space in domain shows error")
    void TC010_spaceInDomainShowsError() {
        newsletterPage.submitWithEmail("user@my domain.com");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC011 - Email with empty domain shows error")
    void TC011_emptyDomainShowsError() {
        newsletterPage.submitWithEmail("user@.com");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC012 - Email with numbers in extension is accepted")
    void TC012_numbersInExtensionAccepted() {
        newsletterPage.submitWithEmail("user@example.123");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
    }

    @Test
    @DisplayName("TC013 - Email with hyphen in extension is accepted")
    void TC013_hyphenInExtensionAccepted() {
        newsletterPage.submitWithEmail("user@example.co-m");
        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessTitle());
    }

    @Test
    @DisplayName("TC014 - Email with space in extension shows error")
    void TC014_spaceInExtensionShowsError() {
        newsletterPage.submitWithEmail("user@example.c om");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC015 - Email with empty extension shows error")
    void TC015_emptyExtensionShowsError() {
        newsletterPage.submitWithEmail("user@example.");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC016 - Email missing @ symbol shows error")
    void TC016_missingAtSymbolShowsError() {
        newsletterPage.submitWithEmail("userexample.com");
        assertTrue(newsletterPage.isErrorVisible());
    }

    @Test
    @DisplayName("TC017 - Email missing dot before extension shows error")
    void TC017_missingDotBeforeExtensionShowsError() {
        newsletterPage.submitWithEmail("user@examplecom");
        assertTrue(newsletterPage.isErrorVisible());
    }
}
