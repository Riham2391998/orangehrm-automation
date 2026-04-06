package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitHelper - Handles all explicit waits
 *
 * WHY EXPLICIT WAITS?
 * Web pages load asynchronously. If we click a button and immediately
 * look for a result, the result may not be there yet.
 * Explicit waits tell Selenium: "Keep trying until this condition is
 * true, OR timeout after X seconds."
 *
 * We NEVER use Thread.sleep() because:
 * - It wastes time (always waits the full duration)
 * - It makes tests slow and fragile
 */
public class WaitHelper {

    // Default timeout in seconds
    private static final int DEFAULT_TIMEOUT = 20;

    /**
     * Wait until an element is VISIBLE on screen.
     * Use this before reading text from an element.
     */
    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait until an element is CLICKABLE.
     * Use this before clicking buttons, links, etc.
     */
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait until an element is VISIBLE, with a custom timeout.
     */
    public static WebElement waitForVisibility(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for a specific WebElement (already found) to be visible.
     */
    public static WebElement waitForElementVisible(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait until the page URL CONTAINS a specific text.
     * Useful for checking navigation happened correctly.
     */
    public static void waitForUrlContains(WebDriver driver, String urlFragment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Wait until a specific TEXT appears somewhere on the page.
     */
    public static void waitForTextPresent(WebDriver driver, By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait until an element DISAPPEARS from the page.
     * Useful for waiting for loading spinners to go away.
     */
    public static void waitForInvisibility(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
