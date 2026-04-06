package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * DriverManager - Manages the WebDriver lifecycle
 *
 * This class handles creating and destroying the browser.
 * We use ThreadLocal so each test thread gets its OWN browser instance
 * (important if you ever run tests in parallel).
 */
public class DriverManager {

    // ThreadLocal ensures each thread has its own WebDriver instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initialize the ChromeDriver and open the browser.
     * Call this at the START of each test class.
     */
    public static void initDriver() {
        // WebDriverManager automatically downloads the correct ChromeDriver
        // No need to manually download chromedriver.exe!
        WebDriverManager.chromedriver().setup();

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();

        // Run headless in CI/CD environments (no visible browser window)
        // Comment out the next two lines if you want to SEE the browser
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");

        // Create the driver and store it
        driver.set(new ChromeDriver(options));

        // Maximize the window
        getDriver().manage().window().maximize();
    }

    /**
     * Get the current WebDriver instance.
     * Use this everywhere you need to interact with the browser.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Close the browser and clean up.
     * Call this at the END of each test class.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Remove from ThreadLocal to prevent memory leaks
        }
    }
}
