package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all page objects providing common functionality.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected static final int DEFAULT_TIMEOUT_SECONDS = 10;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks an element using JavaScript executor for reliability.
     */
    protected void clickElement(WebElement element) {
        waitForClickability(element).click();
    }

    /**
     * Clicks an element using JavaScript executor for reliability.
     */
    protected void clickElementWithJs(WebElement element) {
        WebElement clickable = waitForClickability(element);
        executeJs("arguments[0].click();", clickable);
    }

    /**
     * Waits for an element to be visible and returns it.
     */
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be clickable and returns it.
     */
    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for an element to be clickable and returns it.
     */
    protected WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Checks if an element is displayed without throwing exceptions.
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets text from an element safely.
     */
    protected String getElementText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Executes JavaScript code.
     */
    protected Object executeJs(String script, Object... args) {
        return ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script, args);
    }

    /**
     * Waits for URL to contain a specific string.
     */
    protected boolean waitForUrlContains(String substring) {
        try {
            wait.until(ExpectedConditions.urlContains(substring));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Finds multiple elements.
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Finds a single element.
     */
    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
}
