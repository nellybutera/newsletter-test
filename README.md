# Newsletter Sign-Up Form — Selenium Test Suite

Automated end-to-end tests for the [newsletter sign-up form](https://nellybutera.github.io/newsletter/) built with Selenium WebDriver and JUnit 5.

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Selenium WebDriver | 4.27.0 | Browser automation |
| JUnit 5 | 5.10.2 | Test framework & parameterized tests |
| Maven | 3.x | Build and dependency management |
| SLF4J + Logback | 2.0.9 / 1.4.14 | Logging |

## Project Structure

```
src/
├── main/java/com/automation/pages/
│   ├── BasePage.java              # Shared WebDriver + WebDriverWait base
│   ├── NewsletterPage.java        # Page Object for the sign-up form
│   └── SuccessModal.java          # Page Object for the success confirmation modal
└── test/
    ├── java/com/automation/
    │   ├── base/SetUp.java         # Browser lifecycle + per-test TestWatcher
    │   └── tests/NewsletterTest.java  # 17 test cases (TC001–TC017)
    └── resources/
        └── logback-test.xml        # Logging configuration for test runs
```

## Running Tests

```bash
# Chrome, headed (default)
mvn test

# Chrome, headless
mvn test -Dheadless=true

# Firefox, headless
mvn test -Dbrowser=firefox -Dheadless=true
```

## Test Cases

| TC | Email input | Expected result |
|---|---|---|
| TC001 | `test@example.com` | Success modal + confirmed address |
| TC002 | `invalid` | Error: "Valid email required" |
| TC003 | *(empty)* | Error: "Valid email required" |
| TC004 | `john doe@example.com` | Error (space in username) |
| TC005 | `user123@example.com` | Success (numbers in username) |
| TC006 | `user_test@example.com` | Success (underscore in username) |
| TC007 | `@example.com` | Error (empty username) |
| TC008 | `user@123.com` | Success (numbers in domain) |
| TC009 | `user@my-domain.com` | Success (hyphen in domain) |
| TC010 | `user@my domain.com` | Error (space in domain) |
| TC011 | `user@.com` | Error (empty domain) |
| TC012 | `user@example.123` | Success (numbers in extension) |
| TC013 | `user@example.co-m` | Success (hyphen in extension) |
| TC014 | `user@example.c om` | Error (space in extension) |
| TC015 | `user@example.` | Error (empty extension) |
| TC016 | `userexample.com` | Error (missing @) |
| TC017 | `user@examplecom` | Error (missing dot in domain) |

## CI/CD

GitHub Actions runs on every push and pull request to `main`, `develop`, and `feature/*` branches, and can also be triggered manually via `workflow_dispatch`.

On completion, a Slack message and email are sent with:
- Pass/Fail status
- Total / Passed / Failed / Skipped counts
- Names of any failing test cases
