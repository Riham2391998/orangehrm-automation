package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverManager;

/**
 * BaseTest - Parent class for ALL test classes
 *
 * WHY DO WE NEED THIS?
 * Every test needs to:
 *   1. Start the browser BEFORE the test
 *   2. Close the browser AFTER the test
 *   3. Generate a report entry
 *
 * Instead of repeating this in every test class, we put it here.
 * TestNG @Before/@After annotations handle the lifecycle automatically.
 *
 * TESTNG ANNOTATION LIFECYCLE:
 *   @BeforeSuite  → runs ONCE before ALL tests
 *   @BeforeMethod → runs before EACH test method
 *   @AfterMethod  → runs after EACH test method
 *   @AfterSuite   → runs ONCE after ALL tests
 */
public class BaseTest {

    // ExtentReports: HTML test report generator
    protected static ExtentReports extent;
    protected ExtentTest test; // Represents one test in the report
    protected WebDriver driver;

    /**
     * Set up the ExtentReports reporter.
     * Runs ONCE before all tests start.
     */
    @BeforeSuite
    public void setUpReporter() {
        // Where to save the HTML report
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setReportName("OrangeHRM Automation Report");
        spark.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Application", "OrangeHRM Demo");
        extent.setSystemInfo("Environment", "https://opensource-demo.orangehrmlive.com");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Tester", "QA Automation Team");

        System.out.println(">>> ExtentReports initialized");
    }

    /**
     * Start the browser before each test.
     * The test name is used for the report entry.
     */
    @BeforeMethod
    public void setUp(java.lang.reflect.Method method) {
        // Initialize the browser
        DriverManager.initDriver();
        driver = DriverManager.getDriver();

        // Create a test entry in the report
        test = extent.createTest(method.getName());
        System.out.println(">>> Starting test: " + method.getName());
    }

    /**
     * Close the browser and log pass/fail after each test.
     * ITestResult gives us access to the test result.
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log result to ExtentReports
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test FAILED: " + result.getThrowable());
            System.out.println(">>> Test FAILED: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test PASSED");
            System.out.println(">>> Test PASSED: " + result.getName());
        } else {
            test.skip("Test SKIPPED");
        }

        // Close the browser
        DriverManager.quitDriver();
    }

    /**
     * Flush (save) the report after ALL tests finish.
     */
    @AfterSuite
    public void tearDownReporter() {
        if (extent != null) {
            extent.flush();
            System.out.println(">>> ExtentReports saved to test-output/ExtentReport.html");
        }
    }
}
