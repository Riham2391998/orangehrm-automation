package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.WaitHelper;

/**
 * BasePage - The PARENT class for all Page classes
 *
 * WHY DO WE NEED THIS?
 * Every page class needs the WebDriver.
 * Instead of passing it to every method in every class,
 * we put the common stuff HERE, and all page classes "extend" BasePage.
 *
 * This is the foundation of the Page Object Model (POM) pattern.
 */
public class BasePage {

    // Every page needs the WebDriver to interact with the browser
    protected WebDriver driver;

    /**
     * Constructor: Takes the driver and initializes @FindBy elements.
     * PageFactory.initElements() connects @FindBy annotations to real elements.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        // This initializes all @FindBy fields in child classes
        PageFactory.initElements(driver, this);
    }

    // ================================================================
    // HELPER METHODS - available to ALL page classes
    // ================================================================

    /**
     * Click an element safely (waits until clickable first)
     */
    protected void click(By locator) {
        WaitHelper.waitForClickable(driver, locator).click();
    }

    /**
     * Type text into an input field (clears it first, then types)
     */
    protected void type(By locator, String text) {
        WebElement element = WaitHelper.waitForVisibility(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get the text content of an element
     */
    protected String getText(By locator) {
        return WaitHelper.waitForVisibility(driver, locator).getText();
    }

    /**
     * Check if an element is visible on the page
     */
    protected boolean isDisplayed(By locator) {
        try {
            return WaitHelper.waitForVisibility(driver, locator, 5).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Navigate to a URL
     */
    protected void navigateTo(String url) {
        driver.get(url);
    }

    /**
     * Get the current page URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
