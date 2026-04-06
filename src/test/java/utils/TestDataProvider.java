package utils;

import org.testng.annotations.DataProvider;

/**
 * TestDataProvider - Supplies test data for Data-Driven Testing
 *
 * WHAT IS DATA-DRIVEN TESTING?
 * Instead of hardcoding one employee name, we run the same test
 * multiple times with DIFFERENT data each time.
 * This lets us test many scenarios with one test method.
 *
 * The @DataProvider annotation tells TestNG: "this method provides
 * data to test methods that reference it."
 */
public class TestDataProvider {

    /**
     * Provides employee data for multiple test runs.
     *
     * Each inner array = one test run:
     * { firstName, lastName, employeeId }
     *
     * The test will run 3 times — once per row.
     */
    @DataProvider(name = "employeeData")
    public static Object[][] getEmployeeData() {
        return new Object[][] {
            // { firstName, lastName, employeeId }
            { "Alice",   "Johnson",  "EMP001" },
            { "Bob",     "Williams", "EMP002" },
            { "Charlie", "Brown",    "EMP003" }
        };
    }
}
