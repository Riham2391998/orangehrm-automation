package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

/**
 * PimPage - Represents the PIM (Personnel Information Management) Module
 *
 * PIM is where HR managers manage employee records.
 * From this page, we can:
 * - See the list of employees
 * - Click "Add" to create a new employee
 * - Search for employees
 */
public class PimPage extends BasePage {

    // ================================================================
    // LOCATORS
    // ================================================================

    // The "Add" button to create a new employee
    private final By addEmployeeButton = By.cssSelector("button[class*='button--secondary']");

    // The PIM module header — confirms we're on the right page
    private final By pimHeader = By.cssSelector(".oxd-topbar-header-breadcrumb h6");

    // Employee List table (to confirm we're on the employee list page)
    private final By employeeListTable = By.cssSelector(".oxd-table");

    // ================================================================
    // CONSTRUCTOR
    // ================================================================

    public PimPage(WebDriver driver) {
        super(driver);
    }

    // ================================================================
    // ACTIONS
    // ================================================================

    /**
     * Confirm we are on the PIM module
     */
    public boolean isOnPimPage() {
        WaitHelper.waitForUrlContains(driver, "viewPimModule");
        return isDisplayed(pimHeader);
    }

    /**
     * Click the "Add" button to go to the Add Employee page
     */
    public AddEmployeePage clickAddEmployee() {
        click(addEmployeeButton);
        return new AddEmployeePage(driver);
    }
}
