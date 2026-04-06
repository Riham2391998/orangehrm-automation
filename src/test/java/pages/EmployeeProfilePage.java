package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

/**
 * EmployeeProfilePage - Represents the Employee Profile page
 *
 * After saving a new employee, OrangeHRM redirects here.
 * We use this page to VERIFY the employee was created successfully.
 */
public class EmployeeProfilePage extends BasePage {

    // ================================================================
    // LOCATORS
    // ================================================================

    // The employee's first name shown on the profile
    private final By firstNameField = By.name("firstName");

    // The employee's last name shown on the profile
    private final By lastNameField = By.name("lastName");

    // The employee ID displayed on the profile
    private final By employeeIdField = By.cssSelector(
        ".orangehrm-employee-container .oxd-input"
    );

    // The profile page header
    private final By profileHeader = By.cssSelector(".orangehrm-main-title");

    // Success toast message (shown briefly after save)
    private final By successToast = By.cssSelector(".oxd-toast--success");

    // ================================================================
    // CONSTRUCTOR
    // ================================================================

    public EmployeeProfilePage(WebDriver driver) {
        super(driver);
    }

    // ================================================================
    // VERIFICATION METHODS
    // ================================================================

    /**
     * Wait for profile page to load after saving.
     * OrangeHRM redirects to /viewPersonalDetails after save.
     */
    public boolean isProfilePageLoaded() {
        WaitHelper.waitForUrlContains(driver, "viewPersonalDetails");
        return isDisplayed(firstNameField);
    }

    /**
     * Get the employee's first name from the profile.
     * We use getAttribute("value") because it's an input field
     * (input fields show their content via the "value" attribute, not getText())
     */
    public String getFirstName() {
        WaitHelper.waitForVisibility(driver, firstNameField);
        return driver.findElement(firstNameField).getAttribute("value");
    }

    /**
     * Get the employee's last name from the profile.
     */
    public String getLastName() {
        WaitHelper.waitForVisibility(driver, lastNameField);
        return driver.findElement(lastNameField).getAttribute("value");
    }

    /**
     * Check if the success toast notification was shown.
     * This confirms the save was successful.
     */
    public boolean isSuccessToastDisplayed() {
        return isDisplayed(successToast);
    }

    /**
     * Verify that the correct employee was saved.
     * Returns true if first AND last name match what we entered.
     */
    public boolean isEmployeeCreated(String expectedFirstName, String expectedLastName) {
        boolean profileLoaded = isProfilePageLoaded();
        if (!profileLoaded) return false;

        String actualFirst = getFirstName();
        String actualLast = getLastName();

        System.out.println("Expected: " + expectedFirstName + " " + expectedLastName);
        System.out.println("Actual:   " + actualFirst + " " + actualLast);

        return actualFirst.equalsIgnoreCase(expectedFirstName)
            && actualLast.equalsIgnoreCase(expectedLastName);
    }
}
