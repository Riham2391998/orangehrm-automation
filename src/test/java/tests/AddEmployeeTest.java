package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestDataProvider;

/**
 * AddEmployeeTest - End-to-End test for adding a new employee
 *
 * This is the MAIN TEST that covers the full scenario:
 *   1. Login
 *   2. Navigate to PIM module
 *   3. Add a new employee
 *   4. Verify the employee was added successfully
 *   5. Logout
 *
 * We have TWO test methods:
 *   - testAddSingleEmployee() → simple test, one employee
 *   - testAddMultipleEmployees() → data-driven, multiple employees
 */
public class AddEmployeeTest extends BaseTest {

    private static final String USERNAME = "Admin";
    private static final String PASSWORD = "admin123";

    // ================================================================
    // TEST 1: Add a single employee (simple, easy to follow)
    // ================================================================

    /**
     * Full end-to-end test: Login → PIM → Add Employee → Verify → Logout
     */
    @Test(description = "End-to-end: Login, add employee, verify, logout")
    public void testAddSingleEmployee() {

        // ---- STEP 1: Login ----
        test.info("Step 1: Logging in...");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        DashboardPage dashboardPage = loginPage.login(USERNAME, PASSWORD);

        Assert.assertTrue(dashboardPage.isLoggedIn(),
            "Login failed! Dashboard not displayed.");
        test.pass("Step 1 PASSED: Login successful");

        // ---- STEP 2: Navigate to PIM ----
        test.info("Step 2: Navigating to PIM module...");
        PimPage pimPage = dashboardPage.navigateToPim();

        Assert.assertTrue(pimPage.isOnPimPage(),
            "Navigation to PIM failed! PIM page not loaded.");
        test.pass("Step 2 PASSED: PIM module loaded");

        // ---- STEP 3: Click Add Employee ----
        test.info("Step 3: Clicking 'Add Employee' button...");
        AddEmployeePage addEmployeePage = pimPage.clickAddEmployee();

        Assert.assertTrue(addEmployeePage.isOnAddEmployeePage(),
            "Add Employee page did not load!");
        test.pass("Step 3 PASSED: Add Employee form opened");

        // ---- STEP 4: Fill in employee details ----
        test.info("Step 4: Filling in employee details...");
        String firstName  = "John";
        String lastName   = "Doe";
        String employeeId = "EMP" + System.currentTimeMillis() % 10000; // Unique ID

        EmployeeProfilePage profilePage =
            addEmployeePage.addEmployee(firstName, lastName, employeeId);

        // ---- STEP 5: Verify the employee was created ----
        test.info("Step 5: Verifying employee was created successfully...");

        boolean isCreated = profilePage.isEmployeeCreated(firstName, lastName);

        Assert.assertTrue(isCreated,
            "Employee verification failed! Name does not match on profile page.");

        test.pass("Step 5 PASSED: Employee '" + firstName + " " + lastName + "' created successfully!");

        // ---- STEP 6: Logout ----
        test.info("Step 6: Logging out...");
        DashboardPage dashboard = new DashboardPage(driver);
        LoginPage logoutPage = dashboard.logout();

        Assert.assertTrue(logoutPage.isOnLoginPage(),
            "Logout failed! Login page not displayed.");
        test.pass("Step 6 PASSED: Logged out successfully");

        // Final summary
        test.pass("✅ Full end-to-end test PASSED!");
    }

    // ================================================================
    // TEST 2: Data-Driven Test (adds multiple employees from test data)
    // ================================================================

    /**
     * DATA-DRIVEN TEST: Runs the same test with multiple sets of data.
     *
     * The dataProvider = "employeeData" refers to the @DataProvider
     * method in TestDataProvider.java.
     *
     * This test will run 3 times (once per row in the data provider):
     *   - Alice Johnson (EMP001)
     *   - Bob Williams  (EMP002)
     *   - Charlie Brown (EMP003)
     *
     * The parameters (firstName, lastName, employeeId) are automatically
     * injected by TestNG from the data provider.
     */
    @Test(
        description     = "Data-driven test: Add multiple employees",
        dataProvider    = "employeeData",
        dataProviderClass = TestDataProvider.class
    )
    public void testAddMultipleEmployees(String firstName, String lastName, String employeeId) {

        test.info("Testing with data: " + firstName + " " + lastName + " (ID: " + employeeId + ")");

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DashboardPage dashboardPage = loginPage.login(USERNAME, PASSWORD);
        Assert.assertTrue(dashboardPage.isLoggedIn(), "Login failed for: " + firstName);

        // Navigate to PIM
        PimPage pimPage = dashboardPage.navigateToPim();
        Assert.assertTrue(pimPage.isOnPimPage(), "PIM page did not load");

        // Add employee
        AddEmployeePage addEmployeePage = pimPage.clickAddEmployee();
        EmployeeProfilePage profilePage = addEmployeePage.addEmployee(firstName, lastName, employeeId);

        // Verify
        boolean isCreated = profilePage.isEmployeeCreated(firstName, lastName);
        Assert.assertTrue(isCreated,
            "Employee '" + firstName + " " + lastName + "' was NOT created correctly!");

        // Logout
        DashboardPage dash = new DashboardPage(driver);
        dash.logout();

        test.pass("Employee '" + firstName + " " + lastName + "' added and verified!");
    }
}
