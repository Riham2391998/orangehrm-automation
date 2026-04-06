package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

/**
 * AddEmployeePage - Represents the "Add Employee" form
 *
 * This page contains a form where we fill in employee details.
 * After saving, we land on the Employee Profile page.
 */
public class AddEmployeePage extends BasePage {

    // ================================================================
    // LOCATORS
    // ================================================================

    // First name input field
    private final By firstNameInput = By.name("firstName");

    // Middle name input field
    private final By middleNameInput = By.name("middleName");

    // Last name input field
    private final By lastNameInput = By.name("lastName");

    // Employee ID field (auto-generated, but we can change it)
    private final By employeeIdInput = By.cssSelector(
        "input.oxd-input[class*='oxd-input']"
    );

    // The Save button
    private final By saveButton = By.cssSelector("button[type='submit']");

    // Page title — confirms we're on the add employee page
    private final By pageTitle = By.cssSelector(".orangehrm-main-title");

    // Success toast notification (green bar after saving)
    private final By successToast = By.cssSelector(".oxd-toast-content");

    // ================================================================
    // CONSTRUCTOR
    // ================================================================

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    // ================================================================
    // ACTIONS
    // ================================================================

    /**
     * Check we're on the Add Employee page
     */
    public boolean isOnAddEmployeePage() {
        WaitHelper.waitForUrlContains(driver, "addEmployee");
        return isDisplayed(pageTitle);
    }

    /**
     * Enter the first name
     */
    public AddEmployeePage enterFirstName(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    /**
     * Enter the last name
     */
    public AddEmployeePage enterLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    /**
     * Enter the Employee ID.
     *
     * NOTE: The Employee ID field is tricky — it's auto-filled by OrangeHRM.
     * We need to clear it and type a new one.
     * We use the 4th input field on the page (index 3, zero-based).
     */
    public AddEmployeePage enterEmployeeId(String employeeId) {
        // Find all inputs, select the Employee ID one (index 3 on the page)
        java.util.List<org.openqa.selenium.WebElement> inputs =
            driver.findElements(By.cssSelector("input.oxd-input"));

        if (inputs.size() > 3) {
            org.openqa.selenium.WebElement empIdField = inputs.get(3);
            // Wait for it to be visible
            WaitHelper.waitForElementVisible(driver, empIdField);
            empIdField.clear();
            empIdField.sendKeys(employeeId);
        }
        return this;
    }

    /**
     * Click Save to submit the form.
     * Returns EmployeeProfilePage — after saving, OrangeHRM redirects there.
     */
    public EmployeeProfilePage clickSave() {
        click(saveButton);
        return new EmployeeProfilePage(driver);
    }

    /**
     * Full convenience method: Fill in all fields and save.
     */
    public EmployeeProfilePage addEmployee(String firstName, String lastName, String employeeId) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmployeeId(employeeId);
        return clickSave();
    }
}
