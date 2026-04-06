package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

/**
 * LoginTest - Tests for the Login functionality
 *
 * We test two scenarios:
 *   1. Successful login with correct credentials
 *   2. Failed login with wrong password (negative test)
 *
 * HOW TESTS WORK:
 *   - @Test marks a method as a test case
 *   - Assert.assertTrue() / Assert.assertEquals() verify expected behaviour
 *   - If an assertion fails, the test FAILS immediately
 */
public class LoginTest extends BaseTest {

    // The URL and credentials (kept here for easy maintenance)
    private static final String URL      = "https://opensource-demo.orangehrmlive.com";
    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "admin123";

    /**
     * TEST 1: Successful Login
     *
     * Steps:
     *   1. Open the login page
     *   2. Enter valid credentials
     *   3. Click Login
     *   4. Verify we land on the Dashboard
     */
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        // Step 1: Open login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        test.info("Opened login page");

        // Step 2 & 3: Login
        DashboardPage dashboardPage = loginPage.login(USERNAME, PASSWORD);
        test.info("Entered credentials and clicked Login");

        // Step 4: Verify login was successful
        boolean isLoggedIn = dashboardPage.isLoggedIn();
        test.info("Checking if dashboard is displayed...");

        // ASSERTION: This will FAIL the test if isLoggedIn is false
        Assert.assertTrue(isLoggedIn,
            "Dashboard was not displayed after login — login may have failed!");

        test.pass("Login successful! Dashboard is displayed.");
    }

    /**
     * TEST 2: Failed Login (Negative Test)
     *
     * Steps:
     *   1. Open the login page
     *   2. Enter WRONG password
     *   3. Click Login
     *   4. Verify an error message appears
     */
    @Test(description = "Verify error message appears with invalid credentials")
    public void testFailedLogin() {
        // Step 1: Open login page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        test.info("Opened login page");

        // Step 2 & 3: Login with wrong password
        loginPage.enterUsername(USERNAME);
        loginPage.enterPassword("wrongpassword123");
        loginPage.clickLogin();
        test.info("Entered wrong credentials and clicked Login");

        // Step 4: Verify error message is shown
        boolean isErrorDisplayed = loginPage.isErrorDisplayed();

        Assert.assertTrue(isErrorDisplayed,
            "Error message was not displayed for invalid credentials!");

        test.pass("Error message correctly shown for invalid login.");
    }
}
