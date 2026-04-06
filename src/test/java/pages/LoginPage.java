package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Represents the OrangeHRM Login Screen
 *
 * PAGE OBJECT MODEL (POM) PRINCIPLE:
 * Each page of the website gets its OWN class.
 * The class contains:
 *   1. LOCATORS - how to find elements on the page
 *   2. ACTIONS  - methods that represent what a user can DO on this page
 *
 * Tests should NEVER contain locators directly.
 * Tests call methods like loginPage.login("Admin", "admin123")
 * instead of driver.findElement(...).sendKeys(...)
 *
 * This way, if the UI changes, you update ONE place (the page class),
 * not every test that uses that page.
 */
public class LoginPage extends BasePage {

    // ================================================================
    // LOCATORS - How to find each element
    // We use CSS selectors and name attributes (more stable than XPath)
    // ================================================================

    // The username input field
    private final By usernameInput = By.name("username");

    // The password input field
    private final By passwordInput = By.name("password");

    // The Login button
    private final By loginButton = By.cssSelector("button[type='submit']");

    // Error message (shown when login fails)
    private final By errorMessage = By.cssSelector(".oxd-alert-content-text");

    // The OrangeHRM logo (proof we're on the login page)
    private final By orangeHrmLogo = By.cssSelector(".orangehrm-login-logo");

    // ================================================================
    // CONSTRUCTOR
    // ================================================================

    public LoginPage(WebDriver driver) {
        super(driver); // Call BasePage constructor
    }

    // ================================================================
    // ACTIONS - Methods representing user interactions
    // ================================================================

    /**
     * Open the OrangeHRM login page in the browser
     */
    public LoginPage open() {
        navigateTo("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        return this;
    }

    /**
     * Type the username into the username field
     */
    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this; // Return 'this' allows method chaining: page.enterUsername().enterPassword()
    }

    /**
     * Type the password into the password field
     */
    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    /**
     * Click the Login button
     * Returns a DashboardPage because after login, we land on the dashboard
     */
    public DashboardPage clickLogin() {
        click(loginButton);
        return new DashboardPage(driver);
    }

    /**
     * Convenience method: Do the full login in one call
     * Example: loginPage.login("Admin", "admin123")
     */
    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    /**
     * Check if the error message is visible (for negative test cases)
     */
    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Get the error message text
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if we are actually on the Login page
     */
    public boolean isOnLoginPage() {
        return isDisplayed(orangeHrmLogo);
    }
}
