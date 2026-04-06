package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

/**
 * DashboardPage - Represents the main dashboard after login
 *
 * This page is the "home screen" of OrangeHRM.
 * From here, we can navigate to other modules like PIM.
 */
public class DashboardPage extends BasePage {

    // ================================================================
    // LOCATORS
    // ================================================================

    // The dashboard header text — confirms we're logged in successfully
    private final By dashboardHeader = By.cssSelector(".oxd-topbar-header-breadcrumb h6");

    // The user menu button (top-right corner with user's name)
    private final By userMenuButton = By.cssSelector(".oxd-userdropdown-tab");

    // The Logout option in the dropdown menu
    private final By logoutOption = By.cssSelector(".oxd-userdropdown-link[href*='logout']");

    // The PIM menu item in the left sidebar
    private final By pimMenuItem = By.cssSelector("a[href*='viewPimModule']");

    // ================================================================
    // CONSTRUCTOR
    // ================================================================

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // ================================================================
    // ACTIONS
    // ================================================================

    /**
     * Verify we are on the dashboard (i.e., login was successful)
     * Returns true if the dashboard header is visible
     */
    public boolean isLoggedIn() {
        // Wait for the page to fully load and show the dashboard
        WaitHelper.waitForUrlContains(driver, "dashboard");
        return isDisplayed(dashboardHeader);
    }

    /**
     * Get the page title text (should be "Dashboard")
     */
    public String getPageTitle() {
        return getText(dashboardHeader);
    }

    /**
     * Navigate to the PIM (Personnel Information Management) module
     * Returns a PimPage object because that's where we'll land
     */
    public PimPage navigateToPim() {
        click(pimMenuItem);
        return new PimPage(driver);
    }

    /**
     * Log out of the application
     * Returns a LoginPage because after logout, we're back at login
     */
    public LoginPage logout() {
        // Click the user menu to open the dropdown
        click(userMenuButton);
        // Click the Logout option
        click(logoutOption);
        return new LoginPage(driver);
    }
}
